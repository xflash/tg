package org.xflash.mousePathBuilding;

import org.lwjgl.util.vector.Vector2f;
import org.xflash.detection.VectorUtils;
import org.xflash.steering.Boid;
import org.xflash.steering.Steering;

import java.util.List;

/**
 * Created by core on 08.02.2016.
 */
public class Flocker implements Steering {
    public static final int NEIGHBORDIST = 50;
    private List<Boid> boids;
    public static final float DESIREDSEPARATION = 25.0f;

    public Flocker(List<Boid> boids) {
        this.boids = boids;
    }

    public Vector2f computeTarget(Vector2f position) {

        Vector2f acceleration = new Vector2f(0, 0);

        Vector2f sep = separate(position);   // Separation
        Vector2f ali = align(position);      // Alignment
        Vector2f coh = cohesion(position);   // Cohesion
        // Arbitrarily weight these forces
        sep.scale(1.5f);
        ali.scale(1.0f);
        coh.scale(1.0f);

        // Add the force vectors to acceleration
        // We could add mass here if we want A = F / M
        Vector2f.add(acceleration, sep, acceleration);
        Vector2f.add(acceleration, ali, acceleration);
        Vector2f.add(acceleration, coh, acceleration);

        return acceleration;
    }


    Vector2f separate(Vector2f position) {
        Vector2f steer = new Vector2f(0, 0);
        int count = 0;
        // For every boid in the system, check if it's too close
        for (Boid other : boids) {
            float d = VectorUtils.distance(position, other.position);
            // If the distance is greater than 0 and less than an arbitrary amount (0 when you are yourself)
            if ((d > 0) && (d < DESIREDSEPARATION)) {
                // Calculate vector pointing away from neighbor
                Vector2f diff = Vector2f.sub(position, other.position, null);
                VectorUtils.normalize(diff);
                diff.scale(1 / d);
//                diff.div(d);        // Weight by distance
                Vector2f.add(steer, diff, steer);
                count++;            // Keep track of how many
            }
        }
        // Average -- divide by how many
        if (count > 0) {
            steer.scale(1 / (float) count);
        }
//
//        // As long as the vector is greater than 0
//        if (steer.length() > 0) {
//            // First two lines of code below could be condensed with new PVector setMag() method
//            // Not using this method until Processing.js catches up
//            // steer.setMag(maxspeed);
//
//            // Implement Reynolds: Steering = Desired - Velocity
//            VectorUtils.normalize(steer);
//            steer.scale(maxVelocity);
//            Vector2f.sub(steer, velocity, steer);
//            VectorUtils.truncate(steer, maxforce);
//        }
        return steer;
    }

    // Alignment
    // For every nearby boid in the system, calculate the average velocity
    Vector2f align(Vector2f position) {
        float neighbordist = 50;
        Vector2f sum = new Vector2f(0, 0);
        int count = 0;
        for (Boid other : boids) {

            float d = VectorUtils.distance(position, other.position);
            if ((d > 0) && (d < neighbordist)) {
                Vector2f.add(sum, other.velocity, sum);
                count++;
            }
        }
        if (count > 0) {
            sum.scale(1 / (float) count);
            // First two lines of code below could be condensed with new PVector setMag() method
            // Not using this method until Processing.js catches up
            // sum.setMag(maxspeed);
//
//            // Implement Reynolds: Steering = Desired - Velocity
//            VectorUtils.normalize(sum);
//            sum.scale(maxVelocity);
//            Vector2f.sub(sum, velocity, sum);
//            VectorUtils.truncate(sum, maxforce);
            return sum;
        } else {
            return new Vector2f(0, 0);
        }
    }

    // Cohesion
    // For the average location (i.e. center) of all nearby boids, calculate steering vector towards that location
    Vector2f cohesion(Vector2f position) {
        float neighbordist = NEIGHBORDIST;
        Vector2f sum = new Vector2f(0, 0);   // Start with empty vector to accumulate all locations
        int count = 0;
        for (Boid other : boids) {
            float d = VectorUtils.distance(position, other.position);
            if ((d > 0) && (d < neighbordist)) {
                Vector2f.add(sum, other.position, sum);
                count++;
            }
        }
        if (count > 0) {
            sum.scale(1 / (float) count);
            return sum;
//            return seek(sum);  // Steer towards the location
        } else {
            return new Vector2f(0, 0);
        }
    }
}
