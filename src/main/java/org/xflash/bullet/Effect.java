package org.xflash.bullet;

import org.lwjgl.util.Point;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 */
public class Effect extends Actor {

    private Point point;
    private Point target;
    private float angle = 0.0f;
    private int length = 100;
    private int timeout;

    @Override
    public void init(Object[] args) {
    }

    @Override
    public void update(GameContainer gc, int delta) {
        timeout -= delta;
        if(timeout<0) {
            angle += .1f;
            resetTimer();
            updateTarget();
            if(angle> 2*Math.PI)
                exists(false);
        }
    }

    private void resetTimer() {
        timeout=5;
    }

    private void updateTarget() {
        target = new Point(point.getX() + ((int) (Math.cos(angle) * length)), point.getY() + ((int) (Math.sin(angle) * length)));
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        g.drawLine(point.getX(), point.getY(), target.getX(), target.getY());
//        g.drawLine(point.getX(), point.getY(), point.getX()+5, point.getY()+5);
//        g.drawLine(point.getX(), point.getY(), point.getX()+5, point.getY()-5);
//        g.drawLine(point.getX(), point.getY(), point.getX(), point.getY()-5);
//        g.drawLine(point.getX(), point.getY(), point.getX(), point.getY()+5);
//        g.drawLine(point.getX(), point.getY(), point.getX() - 5, point.getY() + 5);
    }

    public void spawn(int x, int y) {
        point = new Point(x, y);
        resetTimer();
        updateTarget();

        exists(true);
    }
}
