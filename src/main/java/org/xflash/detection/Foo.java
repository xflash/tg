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
    //    private final Vector2f position = new Vector2f();
    private final Sight sight;
    private final Vector2f location = new Vector2f();
    private Path path;
    private int pathIdx;
    private double moveAngle;
    private float speed = .10f;

    public Foo(List<Sightable> sightables) {
        sight = new Sight(SIGHT_RADIUS, sightables);
    }

    public void moveTo(float x, float y) {
//        position.set(x, y);
        location.set(x, y);
        sight.moveTo(location.x, location.y);
    }

    public void update(GameContainer container, int delta) {

        updatePath(delta);

        sight.update(container, delta);
    }

    private boolean updatePath(int delta) {
        if (pathIdx < 0)
            return true;
        Path.Step step = path.getStep(pathIdx);

        if (goToStep(step, delta)) {
            pathIdx++;
            if (pathIdx >= path.getLength()) {
                System.out.println("Resting to step 0");
                pathIdx = 0;
            }
            System.out.println("Moving to next step " + pathIdx);
            resetAngle();
        }
        return false;
    }

    private void resetAngle() {
        Path.Step step = path.getStep(pathIdx);
        moveAngle = AngleUtils.getTargetAngle(step.getX(), step.getY(), location.x, location.y);
        sight.lookAt(moveAngle);
    }

    private boolean goToStep(Path.Step step, int delta) {

        float dx = (float) (Math.cos(moveAngle) * speed * delta);
        float dy = (float) (Math.sin(moveAngle) * speed * delta);
        moveTo(location.x + dx, location.y + dy);

        float xDelta = location.x - step.getX();
        float yDelta = location.y - step.getY();
        return xDelta * xDelta + yDelta * yDelta < 9;
    }


    public void render(GameContainer container, Graphics g) {
        g.setColor(Color.gray);
//        g.drawLine(position.x, position.y, position.x, position.y);
        g.drawRect(location.x - SIZE / 2, location.y - SIZE / 2, SIZE, SIZE);
        sight.render(container, g);
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
