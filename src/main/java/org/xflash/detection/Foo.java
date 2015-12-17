package org.xflash.detection;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.util.pathfinding.Path;
import org.xflash.utils.AngleUtils;

/**
 */
public class Foo {
    private static final int SIGHT_RADIUS = 50;
    private final Vector2f position;
    private final Sight sight;
    private Path path;
    private int pathIdx;
    private final Rectangle shape;
    private double moveAngle;
    private float speed;

    public Foo(int x, int y) {
        shape = new Rectangle(x, y, 15, 15);
        position = new Vector2f();
        sight = new Sight(SIGHT_RADIUS);
        moveTo(x, y);
    }

    public void moveTo(int x, int y) {
        shape.setLocation(x, y);
        position.set(x, y);
        sight.moveTo(shape.getCenterX(), shape.getCenterY());
    }

    public void update(GameContainer container, int delta) {

        if (pathIdx < 0)
            return;
        Path.Step step = path.getStep(pathIdx);

        if (goToStep(step, delta)) {
            pathIdx++;
            if (pathIdx >= path.getLength()) {
                System.out.println("Resting to step 0");
                pathIdx = 0;
            }
            System.out.println("Moving to next step "+ pathIdx);
        }

        sight.update(container, delta);
    }

    private boolean goToStep(Path.Step step, int delta) {
        moveAngle = AngleUtils.getTargetAngle(step.getX(), step.getY(), shape.getCenterX(), shape.getCenterY());
        speed = .20f;
        double dx = Math.cos(moveAngle) * delta * speed;
        double dy = Math.sin(moveAngle) * delta * speed;
        moveTo((int) (shape.getX() + dx), (int) (shape.getY() + dy));

        Circle circle = new Circle(step.getX(), step.getY(), 3);

        return circle.contains(shape.getCenterX(), circle.getCenterY());
    }



    public void render(GameContainer container, Graphics g) {
        g.setColor(Color.gray);
        g.draw(shape);
        sight.render(container, g);
    }

    public void follow(Path path) {
        this.path = path;
        pathIdx = -1;
        int length = path.getLength();
        if (length >= 0) pathIdx = 0;
    }
}
