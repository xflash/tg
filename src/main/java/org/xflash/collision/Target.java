package org.xflash.collision;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

/**
 */
public class Target implements Collidable {

    public static final float RADIUS = 15.f;
    private Shape shape;

    public Target(GameContainer gc, int x, int y) {
        shape = new Circle(x, y, RADIUS);
    }

    public void update(GameContainer gc, int delta) {

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

    public void handleCollision() {
        System.out.println("COLLISION ");
        shape = shape.transform(Transform.createRotateTransform(.10f, shape.getCenterX(), shape.getCenterY()));
    }
}
