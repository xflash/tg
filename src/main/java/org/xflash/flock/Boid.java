package org.xflash.flock;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;
import org.xflash.detection.VectorUtils;

import java.util.ArrayList;

import static java.lang.Math.random;

/**
 * The Boid class
 */
public class Boid {
    private final Polygon shape;
    Vector2f location;
    Vector2f velocity;
    Vector2f acceleration;
    float r;
    float maxforce;    // Maximum steering force
    float maxspeed;    // Maximum speed

    Boid(float x, float y) {
        acceleration = new Vector2f(0, 0);

        // This is a new PVector method not yet implemented in JS
        // velocity = PVector.random2D();

        // Leaving the code temporarily this way so that this example runs in JS
        float angle = (float) (random() * 2 * Math.PI);
        velocity = new Vector2f((float) Math.cos(angle), (float) Math.sin(angle));

        location = new Vector2f(x, y);
        r = 2.0f;
        shape = new Polygon(new float[]{
                0, -r * 2,
                -r, r * 2,
                r, r * 2
        });
        maxspeed = 2;
        maxforce = 0.03f;
    }

    public void update(GameContainer container, ArrayList<Boid> boids) {
        flock(boids);
        update();
        borders(container);
    }

    // Method to update location
    private void update() {
        // Update velocity
        Vector2f.add(velocity, acceleration, velocity);
        // Limit speed
        VectorUtils.truncate(velocity, maxspeed);
        Vector2f.add(location, velocity, location);
        // Reset accelertion to 0 each cycle
        acceleration.scale((float) 0);
    }


    void applyForce2Accelearation(Vector2f force) {
        // We could add mass here if we want A = F / M
        Vector2f.add(acceleration, force, acceleration);
    }

    // We accumulate a new acceleration each time based on three rules
    void flock(ArrayList<Boid> boids) {
        Vector2f sep = separate(boids);   // Separation
        Vector2f ali = align(boids);      // Alignment
        Vector2f coh = cohesion(boids);   // Cohesion
        // Arbitrarily weight these forces
        sep.scale(1.5f);
        ali.scale(1.0f);
        coh.scale(1.0f);
        // Add the force vectors to acceleration
        applyForce2Accelearation(sep);
        applyForce2Accelearation(ali);
        applyForce2Accelearation(coh);
    }


    // A method that calculates and applies a steering force towards a target
    // STEER = DESIRED MINUS VELOCITY
    Vector2f seek(Vector2f target) {
        Vector2f desired = Vector2f.sub(target, location, null);  // A vector pointing from the location to the target
        // Scale to maximum speed
        VectorUtils.normalize(desired);
        desired.scale(maxspeed);

        // Above two lines of code below could be condensed with new PVector setMag() method
        // Not using this method until Processing.js catches up
        // desired.setMag(maxspeed);

        // Steering = Desired minus Velocity
        Vector2f steer = Vector2f.sub(desired, velocity, null);
        VectorUtils.truncate(steer, maxforce);
        return steer;
    }

    public void render(Graphics g) {
        // Draw a triangle rotated in the direction of velocity
        float theta = (float) (VectorUtils.heading2D(velocity) + Math.toRadians(90));
//        float theta = velocity.heading2D() + radians(90);
        // heading2D() above is now heading() but leaving old syntax until Processing.js catches up

//        g.fill
//        fill(200, 100);
//        stroke(255);
        g.translate(location.x, location.y);
        g.rotate(0, 0, theta);
        g.fill(shape);

//        popMatrix();
        g.resetTransform();
    }

    // Wraparound
    void borders(GameContainer container) {
        if (location.x < -r) location.x = container.getWidth() + r;
        if (location.y < -r) location.y = container.getHeight() + r;
        if (location.x > container.getWidth() + r) location.x = -r;
        if (location.y > container.getHeight() + r) location.y = -r;
    }

    // Separation
    // Method checks for nearby boids and steers away
    Vector2f separate(ArrayList<Boid> boids) {
        float desiredseparation = 25.0f;
        Vector2f steer = new Vector2f(0, 0);
        int count = 0;
        // For every boid in the system, check if it's too close
        for (Boid other : boids) {
            float d = VectorUtils.distance(location, other.location);
            // If the distance is greater than 0 and less than an arbitrary amount (0 when you are yourself)
            if ((d > 0) && (d < desiredseparation)) {
                // Calculate vector pointing away from neighbor
                Vector2f diff = Vector2f.sub(location, other.location, null);
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

        // As long as the vector is greater than 0
        if (steer.length() > 0) {
            // First two lines of code below could be condensed with new PVector setMag() method
            // Not using this method until Processing.js catches up
            // steer.setMag(maxspeed);

            // Implement Reynolds: Steering = Desired - Velocity
            VectorUtils.normalize(steer);
            steer.scale(maxspeed);
            Vector2f.sub(steer, velocity, steer);
            VectorUtils.truncate(steer, maxforce);
        }
        return steer;
    }

    // Alignment
    // For every nearby boid in the system, calculate the average velocity
    Vector2f align(ArrayList<Boid> boids) {
        float neighbordist = 50;
        Vector2f sum = new Vector2f(0, 0);
        int count = 0;
        for (Boid other : boids) {

            float d = VectorUtils.distance(location, other.location);
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

            // Implement Reynolds: Steering = Desired - Velocity
            VectorUtils.normalize(sum);
            sum.scale(maxspeed);
            Vector2f steer = Vector2f.sub(sum, velocity, null);
            VectorUtils.truncate(steer, maxforce);
            return steer;
        } else {
            return new Vector2f(0, 0);
        }
    }

    // Cohesion
    // For the average location (i.e. center) of all nearby boids, calculate steering vector towards that location
    Vector2f cohesion(ArrayList<Boid> boids) {
        float neighbordist = 50;
        Vector2f sum = new Vector2f(0, 0);   // Start with empty vector to accumulate all locations
        int count = 0;
        for (Boid other : boids) {
            float d = VectorUtils.distance(location, other.location);
            if ((d > 0) && (d < neighbordist)) {
                Vector2f.add(sum, other.location, sum);
                count++;
            }
        }
        if (count > 0) {
            sum.scale(1 / (float) count);
            return seek(sum);  // Steer towards the location
        } else {
            return new Vector2f(0, 0);
        }
    }


}

