package org.xflash.collision;

import org.lwjgl.util.Point;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.util.InputAdapter;

/**
 */
public class Shooter {

    private Point target;
    private static final int SIZE = 5;
    private Rectangle bbox;

    public Shooter(GameContainer gc, int x, int y) {
        moveTo(x, y);
        target = new Point(x, y);

        gc.getInput().addMouseListener(new InputAdapter(){
            @Override
            public void mouseMoved(int oldx, int oldy, int newx, int newy) {
                target = new Point(newx, newy);
            }
        }); 
    }

    private void moveTo(int x, int y) {
        bbox = new Rectangle(x, y, SIZE, SIZE);
    }

    public void update(GameContainer gc, int delta) {
        
    }

    public void render(GameContainer gc, Graphics g) {
        g.setColor(Color.red);
        g.draw(bbox);
        g.setColor(Color.yellow);
        g.drawLine(bbox.getCenterX(), bbox.getCenterY(), target.getX(), target.getY());
    }
}
