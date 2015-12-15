package org.xflash.collision;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

/**
 */
public class Target {

    public static final float RADIUS = 15.f;
    private final Circle shape;

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
}
