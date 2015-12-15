package org.xflash.collision;

import org.lwjgl.util.Point;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.util.InputAdapter;
import org.newdawn.slick.util.pathfinding.Mover;

/**
 */
public class Shooter implements Mover {

    public static final int SHOOTING_TIMEOUT = 200;
    private static final boolean DEBUG = true;
    private static final float SPEED = 2.5f;
    private static final int SIZE = 15;
    private final BulletPool bulletPool;
    private final CanMove canMove;

    private Point target;
    private Shape bbox;
    private int hmove=0;
    private int vmove=0;
    private boolean shooting = false;
    private int shootingTimeout;

    public Shooter(GameContainer gc, int x, int y, final BulletPool bulletPool, CanMove canMove, final RightClickHandler rightClickHandler) {
        this.bulletPool = bulletPool;
        this.canMove = canMove;
        moveTo(x, y);
        target = new Point(x, y);

        gc.getInput().addListener(new InputAdapter() {
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
            public void mousePressed(int button, int x, int y) {
                if (button == 0) startShooting();
                if (button == 1) rightClickHandler.mousePressed(x, y);
            }

            @Override
            public void mouseReleased(int button, int x, int y) {
                if (button == 0) stopShooting();
                if (button == 1) rightClickHandler.mouseReleased(x, y);
            }

            @Override
            public void mouseMoved(int oldx, int oldy, int newx, int newy) {
                moveTarget(newx, newy);
            }

            @Override
            public void mouseDragged(int oldx, int oldy, int newx, int newy) {
                moveTarget(newx, newy);
            }
        });
    }

    private void moveTarget(int newx, int newy) {
        target = new Point(newx, newy);
    }

    private void startShooting() {
        shooting = true;
        resetShootimngTimeout();
    }

    private void resetShootimngTimeout() {
        shootingTimeout = SHOOTING_TIMEOUT;
    }

    private void stopShooting() {
        shooting = false;
        shootingTimeout = 0;
    }

    private void shoot() {
        Bullet instance = bulletPool.getInstance();
        if (instance != null) {
            instance.spawn(bbox.getCenterX(), bbox.getCenterY(), getTargetAngle(target.getX(), target.getY()));
        }
    }

    private double getTargetAngle(int x, int y) {
        double theta = Math.atan2(y - bbox.getCenterY(), x - bbox.getCenterX());
        return theta < 0 ? theta + Math.PI * 2 : theta;
    }

    private void moveTo(int x, int y) {
        if (bbox == null)
            bbox = new Rectangle(x, y, SIZE, SIZE);
        bbox.setLocation(x, y);
    }

    public void update(GameContainer gc, int delta) {
        float offset = SPEED /** delta*/;
        float newX = bbox.getCenterX() + hmove * offset;
        float newY = bbox.getCenterY() + vmove * offset;
        if (canMove(gc, newX, newY)) {
            bbox.setLocation(bbox.getX() + hmove * offset, bbox.getY() + vmove * offset);
        }
        
        if (shooting) {
            shootingTimeout -= delta;
            if (shootingTimeout < 0) {
                shoot();
                resetShootimngTimeout();
            }
        }
    }

    private boolean canMove(GameContainer gc, float newX, float newY) {
        return canMove.canMove((int) newX, (int) newY)
                && newX >= 0 && (newX + SIZE) <= gc.getWidth() - 1
                && newY >= 0 && (newY + SIZE) <= gc.getHeight() - 1;
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
