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

    private static final boolean DEBUG = true;
    private static final float SPEED = .5f;
    private static final int SIZE = 5;

    private Point target;
    private Shape bbox;
    private int hmove=0;
    private int vmove=0;

    public Shooter(GameContainer gc, int x, int y, final BulletPool bulletPool) {
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

            @Override
            public void mouseClicked(int button, int x, int y, int clickCount) {
                Bullet instance = bulletPool.getInstance();
                if (instance != null) {
                    instance.spawn(bbox.getCenterX(), bbox.getCenterY(), getTargetAngle(x, y));
                }
            }
        });
    }

    private double getTargetAngle(int x, int y) {
        // calculate the angle theta from the deltaY and deltaX values
        // (atan2 returns radians values from [-PI,PI])
        // 0 currently points EAST.  
        // NOTE: By preserving Y and X param order to atan2,  we are expecting 
        // a CLOCKWISE angle direction.  
        double theta = Math.atan2(y - bbox.getCenterY(), x - bbox.getCenterX());
        // rotate the theta angle clockwise by 90 degrees 
        // (this makes 0 point NORTH)
        // NOTE: adding to an angle rotates it clockwise.  
        // subtracting would rotate it counter-clockwise
//        theta += Math.PI/2.0;
        // convert from radians to degrees
        // this will give you an angle from [0->270],[-180,0]
        double angle = Math.toDegrees(theta);

        // convert to positive range [0-360)
        // since we want to prevent negative angles, adjust them now.
        // we can assume that atan2 will not return a negative value
        // greater than one partial rotation
        if (angle < 0) {
            angle += 360;
        }


        return Math.toRadians(angle);
    }


    private void moveTo(int x, int y) {
        bbox = new Rectangle(x, y, SIZE, SIZE);
    }


    public void update(GameContainer gc, int delta) {
        float offset = SPEED * delta;
        bbox = bbox.transform(Transform.createTranslateTransform(hmove * offset, vmove*offset));
    }

    public void render(GameContainer gc, Graphics g) {
        g.setColor(Color.red);
        g.draw(bbox);
        g.setColor(Color.yellow);
        g.drawLine(bbox.getCenterX(), bbox.getCenterY(), target.getX(), target.getY());

        if (DEBUG) {
            g.setColor(Color.magenta);
            g.drawString(String.format("dist: %d", calcDistance()), target.getX()+10, target.getY());
        }
    }

    private int calcDistance() {
        float[] center = bbox.getCenter();
        return (int) Math.sqrt(Math.pow(target.getX()- center[0], 2) + Math.pow(target.getY()- center[1], 2));
    }
}
