package org.xflash.steering;

import org.lwjgl.util.vector.Vector;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;
import org.xflash.detection.VectorUtils;
import org.xflash.utils.AngleUtils;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

/**
 */
public class Boid {

    public static final float MAX_VELOCITY = 3;
    public static final float MAX_FORCE = MAX_VELOCITY * 3;
    private static final int FORCE_SCALE = 100;

    private final Vector2f position;
    private final Vector2f velocity;
    private final Vector2f desired;

    private final float mass;
    private final float maxVelocity;
    private final Polygon polygon;
    private double rotation;
    private Steering steerer;

    public Boid(float posX, float posY, float totalMass, Steering steerer) {

        position = new Vector2f(posX, posY);
        velocity = new Vector2f(-1, -2);
        desired = new Vector2f(0, 0);
        mass = totalMass;
        this.steerer = steerer;

        Random random = new Random();
        maxVelocity = MAX_VELOCITY * (0.8f + random.nextFloat() * 0.2f);

        VectorUtils.truncate(velocity, maxVelocity);

        polygon = new Polygon(new float[]{
                0, -20,
                10, 20,
                -10, 20,
                0, -20,
        });
    }

    public void update(GameContainer container, int delta) {

        Vector2f steering=new Vector2f(0, 0);

        if(steerer!=null) {
            Vector2f target = steerer.computeTarget(position);
            if (target == null) {
                target=new Vector2f(container.getWidth()/2, container.getHeight()/2);
//                target = new Vector2f(position.x, position.y);
            }
            steering=seek(target, position, velocity, maxVelocity, desired);
        }

        VectorUtils.truncate(steering, MAX_FORCE);
        steering.scale(1 / mass);

        VectorUtils.add(velocity, steering);
        VectorUtils.truncate(velocity, maxVelocity);

        VectorUtils.add(position, velocity);

        VectorUtils.normalize(velocity);
        VectorUtils.normalize(steering);
        VectorUtils.normalize(desired);

        // Adjust boid rotation to match the velocity vector.
        rotation = AngleUtils.getTargetAngle(0, 0, velocity.x, velocity.y);
    }

    private Vector2f seek(Vector2f target, Vector2f position, Vector2f velocity, float maxVelocity, Vector2f desired) {
        Vector2f.sub(target, position, desired);
        VectorUtils.normalize(desired);
        desired.scale(maxVelocity);
        return Vector2f.sub(desired, velocity, null);
    }

    public void render(GameContainer container, Graphics g) {
        render(container, g, false);
    }

    public void render(GameContainer container, Graphics g, boolean drawForces) {
        if(drawForces) {
            drawForceVector(g, velocity, new Color(0x00FF00));
            drawForceVector(g, desired, new Color(0x0000FF));
        }

        g.translate(position.x, position.y);
        if(drawForces) {
            g.setColor(new Color(0xEFEFEF));
            g.drawString(String.format("%.2f", mass), 5, 0);
//            g.drawString(String.format("(%.2f, %.2f)", position.x, position.y), 5, -10);
        }

        g.rotate(0, 0, (float) Math.toDegrees(rotation- Math.PI/2));
        g.scale(.5f, .5f);
        g.setColor(new Color(0xFF0000));
        g.fill(polygon);
        g.setLineWidth(2);
        g.setColor(new Color(0xffaabb));
        g.draw(polygon);

        g.resetTransform();
    }

    private void drawForceVector(Graphics g, Vector2f force, Color color) {
        g.setLineWidth(2);
        g.setColor(color);
        g.drawLine(
                position.x,
                position.y,
                position.x + force.x * FORCE_SCALE,
                position.y + force.y * FORCE_SCALE);

        g.drawString(String.format("%.2f,%.2f", force.x, force.y),
                5 + position.x + force.x * FORCE_SCALE,
                5+ position.y + force.y * FORCE_SCALE);


    }


    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public double getX() {
        return position.getX();
    }

    public double getY() {
        return position.getY();
    }
}
