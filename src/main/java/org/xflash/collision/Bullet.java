package org.xflash.collision;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;
import org.xflash.utils.Actor;

import java.util.List;

/**
 */
public class Bullet extends Actor {

    public static final int SPEED = 10;
    public static final int DYING_TIMEOUT = 2000;
    public static final float RADIUS = 2.5f;
    private Shape shape;
    private double angle;
    private int dyingTimeout = 0;
    private List<Collidable> collidables;

    @Override
    public void init(Object[] args) {
        if (args == null || args.length != 1)
            throw new IllegalArgumentException("Wrong number argument passed to Bullet");
        collidables = (List<Collidable>) args[0];
    }

    @Override
    public void update(GameContainer gc, int delta) {

        if (dyingTimeout > 0) {
            dyingTimeout -= delta;
            if (dyingTimeout < 0) die();
        } else {
//            float offset = (/*delta * */SPEED) / 10;
            float offset = SPEED;
            float xOffset = (float) (Math.cos(angle) * offset);
            float yOffset = (float) (Math.sin(angle) * offset);
            shape.setLocation(shape.getX() + xOffset, shape.getY() + yOffset);

            if (shape.getCenterX() < 0 || shape.getCenterY() < 0) dying();
            if (shape.getCenterX() > gc.getWidth() || shape.getCenterY() > gc.getHeight()) dying();

            for (Collidable collidable : collidables) {
                if (collidable.collideWith(shape)) {
                    dying();
                    collidable.handleCollision(shape);
                }
            }

        }
    }

    private void dying() {
        System.out.println("dying ");
        dyingTimeout = DYING_TIMEOUT;
    }

    private void die() {
        System.out.println("die ");
        dyingTimeout = 0;        
        exists(false);
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        g.setColor(Color.orange);
//        g.setColor(dyingTimeout == 0 ? Color.orange : new Color(1.5f, 0.5f, 0.5f, (float) (dyingTimeout / DYING_TIMEOUT)));
        g.fill(shape);
    }

    public void spawn(float x, float y, double angle) {
        System.out.println("spawn = at (" + x + "," + y + ") angle: " + String.format("%.2f", Math.toDegrees(angle)));
        this.angle = angle;
        moveTo(x, y);
        dyingTimeout = 0;
        exists(true);
    }

    private void moveTo(float x, float y) {
        if (shape == null)
            shape = new Circle(x, y, RADIUS);
        shape.setLocation(x, y);
    }
}
