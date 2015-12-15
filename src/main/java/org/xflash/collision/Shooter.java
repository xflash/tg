package org.xflash.collision;

import org.lwjgl.util.Point;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.util.InputAdapter;

/**
 */
public class Shooter {

    private final boolean debug;
    private final float speed = .5f;
    private Point target;
    private static final int SIZE = 5;
    private Shape bbox;
    private int hmove=0;
    private int vmove=0;

    public Shooter(GameContainer gc, int x, int y) {
        moveTo(x, y);
        target = new Point(x, y);

        gc.getInput().addListener(new InputAdapter() {
            @Override
            public void mouseMoved(int oldx, int oldy, int newx, int newy) {
                target = new Point(newx, newy);
            }

            
            @Override
            public void keyPressed(int key, char c) {
                System.out.println("keyPressed = " + key);
                switch (key) {
                    case Input.KEY_LEFT: hmove = -1; break;
                    case Input.KEY_RIGHT: hmove = 1; break;
                    case Input.KEY_UP: vmove = -1; break;
                    case Input.KEY_DOWN: vmove = 1; break;
                    default:
                        break;
                }
            }
            @Override
            public void keyReleased(int key, char c) {
                System.out.println("keyReleased = " + key);
                switch (key) {
                    case Input.KEY_LEFT: case Input.KEY_RIGHT: hmove = 0; break;
                    case Input.KEY_UP: case Input.KEY_DOWN: vmove = 0; break;
                    default:
                        break;
                }
            }
        });
        debug = true;
    }

    private void moveTo(int x, int y) {
        bbox = new Rectangle(x, y, SIZE, SIZE);
    }

    public void update(GameContainer gc, int delta) {
        float offset = speed * delta;
        bbox = bbox.transform(Transform.createTranslateTransform(hmove * offset, vmove*offset));
    }

    public void render(GameContainer gc, Graphics g) {
        g.setColor(Color.red);
        g.draw(bbox);
        g.setColor(Color.yellow);
        g.drawLine(bbox.getCenterX(), bbox.getCenterY(), target.getX(), target.getY());
        
        if(debug) {
            g.setColor(Color.magenta);
            g.drawString(String.format("dist: %d", calcDistance()), target.getX()+10, target.getY());
        }
    }

    private int calcDistance() {
//        Distance =(x2?x1)2+(y2?y1)2???????????????????
        float[] center = bbox.getCenter();
        return (int) Math.sqrt(Math.pow(target.getX()- center[0], 2) + Math.pow(target.getY()- center[1], 2));
    }
}
