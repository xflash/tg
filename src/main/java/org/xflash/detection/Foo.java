package org.xflash.detection;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.util.pathfinding.Path;
import org.xflash.collision.Sightable;
import org.xflash.utils.AngleUtils;

import java.util.List;

/**
 */
public class Foo {
    private static final int SIGHT_RADIUS = 50;
    private static final int SIZE = 15;
    private static final float MAX_VELOCITY = 3;
    private static final float MAX_FORCE = 2.4f;

    //    private final Vector2f position = new Vector2f();
    private final Sight sight;
    private final Vector2f position = new Vector2f();
    private final Vector2f velocity = new Vector2f();
    private final Vector2f target = new Vector2f();
    private final Vector2f desired = new Vector2f();
    private final Vector2f steering=new Vector2f();
    public float mass;
    private Path path;
    private int pathIdx;
    private double moveAngle;
    private float speed = .10f;


    public Foo(List<Sightable> sightables) {
        sight = new Sight(SIGHT_RADIUS, sightables);
        velocity.set(-1, -2);
        this.mass = 20.f;

        VectorUtils.truncate(velocity, MAX_VELOCITY);
    }

    public void moveTo(float x, float y) {
        position.set(x, y);
        sight.moveTo(position.x, position.y);
    }

    public void update(GameContainer container, int delta) {

        target.set(container.getInput().getMouseX(), container.getInput().getMouseY());

        seek(target);
        VectorUtils.truncate(steering, MAX_FORCE);
        steering.scale(1 / mass);

        Vector2f.add(velocity, steering, velocity);
        VectorUtils.truncate(velocity, MAX_VELOCITY);

        Vector2f.add(position, velocity, position);
    }

    private Vector2f seek(Vector2f target) {
        Vector2f.sub(target, position, desired);
        VectorUtils.normalize(desired);

        desired.scale(MAX_VELOCITY);
        return Vector2f.sub(desired, velocity, steering);
    }

    private Vector2f updatePath(int delta) {
        Vector2f target = null;
        if (path != null && pathIdx < 0) {
            Path.Step step = path.getStep(pathIdx);
            target = new Vector2f(step.getX(), step.getY());
            if (distance(target) <= 9) {
                pathIdx++;
                if (pathIdx >= path.getLength()) {
                    pathIdx = path.getLength() - 1;
                }
            }
        }

        return target != null ? seek(target) : new Vector2f();
//
//        float dx = (float) (Math.cos(moveAngle) * speed * delta);
//        float dy = (float) (Math.sin(moveAngle) * speed * delta);
//        moveTo(position.x + dx, position.y + dy);
//
//        float v = distance(step);
//        if (v < 9) {
//            pathIdx++;
//            if (pathIdx >= path.getLength()) {
//                System.out.println("Resting to step 0");
//                pathIdx = 0;
//            }
//            System.out.println("Moving to next step " + pathIdx);
//            resetAngle();
//        }
//        return false;
    }

    private float distance(Vector2f target) {
        float xDelta = position.x - target.getX();
        float yDelta = position.y - target.getY();
        return xDelta * xDelta + yDelta * yDelta;
    }

    private void resetAngle() {
        Path.Step step = path.getStep(pathIdx);
        moveAngle = AngleUtils.getTargetAngle(step.getX(), step.getY(), position.x, position.y);
        sight.lookAt(moveAngle);
    }


    public void render(GameContainer container, Graphics g) {
        g.setColor(Color.gray);
//        g.drawLine(position.x, position.y, position.x, position.y);
        g.drawRect(position.x - SIZE / 2, position.y - SIZE / 2, SIZE, SIZE);
        sight.render(container, g);

        VectorUtils.normalize(velocity);
        VectorUtils.normalize(desired);
        VectorUtils.normalize(steering);

        // Force vectors
        drawForceVector(g, velocity, new Color(0x00FF00));
        drawForceVector(g, desired, new Color(0x454545));
        drawForceVector(g, steering, new Color(0x0000FF));
    }

    private void drawForceVector(Graphics g, Vector2f force, Color color) {
        drawForceVector(g, force, color, 100);
    }
    private void drawForceVector(Graphics g, Vector2f force, Color color, float scale) {

        float oldLineWidth = g.getLineWidth();
        g.translate(position.x + SIZE / 2, position.y + SIZE / 2);
        g.setColor(color);
        g.setLineWidth(2);
        g.drawLine(0,0, position.x + force.x * scale, position.y + force.y * scale);
        g.setLineWidth(oldLineWidth);

        g.resetTransform();
    }


    public void follow(Path path) {
        this.path = path;
        pathIdx = -1;
        int length = path.getLength();
        if (length >= 0) {
            pathIdx = 0;
            resetAngle();
        }
    }


}
