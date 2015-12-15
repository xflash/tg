package org.xflash.collision;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

/**
 */
public class Target implements Collidable {

    public static final float RADIUS = 15.f;
    private Shape shape;
    private float angle;

    public Target(GameContainer gc, int x, int y) {
        moveTo(x, y);
    }

    private void moveTo(int x, int y) {
        if (shape == null) {
//            shape = new Circle(x, y, RADIUS);
            shape = new Ellipse(x, y, RADIUS / 2, RADIUS);
        }
        shape.setLocation(x, y);
    }

    public void update(GameContainer gc, int delta) {
        angle += .1f;
        shape = shape.transform(Transform.createRotateTransform(angle, shape.getCenterX(), shape.getCenterY()));
    }

    public void render(GameContainer gc, Graphics g) {
        g.setColor(Color.darkGray);
        g.fill(shape);
        g.setColor(Color.lightGray);
        g.draw(shape);
    }

    public boolean collideWith(Shape shape) {
        return (this.shape.intersects(shape));
    }

    public void handleCollision(Shape shape) {
        System.out.println("COLLISION ");
        this.shape = this.shape.transform(Transform.createRotateTransform(.10f, this.shape.getCenterX(), this.shape.getCenterY()));
    }
}
