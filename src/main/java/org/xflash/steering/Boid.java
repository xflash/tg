package org.xflash.steering;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;
import org.xflash.detection.VectorUtils;
import org.xflash.utils.AngleUtils;

import java.util.Random;
import java.util.Vector;

/**
 * @author rcoqueugniot
 * @since 18.12.15
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
    private int pathDir;
    public Path path;
    private Vector2f steering;
    private int currentNode;
    private double rotation;
    private int nodeRadius;

    public Boid(float posX, float posY, float totalMass, int nodeRadius) {

        position = new Vector2f(posX, posY);
        velocity = new Vector2f(-1, -2);
        desired = new Vector2f(0, 0);
        steering = new Vector2f(0, 0);
        mass = totalMass;
        path = null;
        currentNode = 0;

        Random random = new Random();
        maxVelocity = MAX_VELOCITY * (0.8f + random.nextFloat() * 0.2f);

        VectorUtils.truncate(velocity, maxVelocity);

        polygon = new Polygon();
//        polygon.addPoint(0, 0);
        polygon.addPoint(0, -20);
        polygon.addPoint(10, 20);
        polygon.addPoint(-10, 20);
        polygon.addPoint(0, -20);

        this.nodeRadius = nodeRadius;
        pathDir = 1;
    }

    public void render(GameContainer container, Graphics g, boolean drawForces) {


        if(drawForces) {
            drawForceVector(g, velocity, new Color(0x00FF00));
            drawForceVector(g, desired, new Color(0x0000FF));
        }

        g.translate(position.x, position.y);
//        g.drawString(String.format("%.2f", mass), 5, 5);

        g.rotate(0, 0, (float) Math.toDegrees(rotation- Math.PI/2));


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


    public void update(GameContainer container, int delta) {
        steering = pathFollowing();

        VectorUtils.truncate(steering, MAX_FORCE);
        steering.scale(1 / mass);

        Vector2f.add(velocity, steering, velocity);
        VectorUtils.truncate(velocity, maxVelocity);

        Vector2f.add(position, velocity, position);


        VectorUtils.normalize(velocity);
        VectorUtils.normalize(steering);
        VectorUtils.normalize(desired);

        // Adjust boid rodation to match the velocity vector.
        rotation = AngleUtils.getTargetAngle(0, 0, velocity.x, velocity.y);
    }

    private Vector2f pathFollowing() {
        Vector2f target = null;

        if (path != null) {
            Vector<Vector2f> nodes = path.getNodes();

            target = nodes.get(currentNode);

            if (VectorUtils.distance(position, target) <= nodeRadius) {
                currentNode += pathDir;
                if (currentNode >= nodes.size() || currentNode<0) {
                    pathDir *= -1;
//                    currentNode = nodes.size() - 1;
//                    currentNode = 0;
                    currentNode += pathDir;
                }
            }
        }
        return target != null ? seek(target) : new Vector2f();
    }

    private Vector2f seek(Vector2f target) {
        Vector2f.sub(target, position, desired);
        VectorUtils.normalize(desired);
        desired.scale(maxVelocity);
        return Vector2f.sub(desired, velocity, null);
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
