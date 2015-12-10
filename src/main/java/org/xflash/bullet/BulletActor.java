package org.xflash.bullet;

import org.lwjgl.util.Point;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 */
public class BulletActor extends Actor {

    private final int size = 10;
    private Point location;

    @Override
    public void init(Object[] args) {
        
    }

    @Override
    public void update(GameContainer gc, int delta) {
        
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        
        g.drawRect(location.getX(), location.getY(), size, size);

    }

    public void spawn(int x, int y) {
        location = new Point(x, y);
        exists(true);
    }

    public Point getCenter() {
        Point point = new Point(location);
        point.translate(size /2, size /2);
        return point;
    }
}
