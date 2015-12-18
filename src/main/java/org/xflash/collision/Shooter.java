package org.xflash.collision;

import org.lwjgl.util.Point;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.util.InputAdapter;
import org.newdawn.slick.util.pathfinding.Mover;
import org.xflash.utils.AngleUtils;

/**
 */
public class Shooter implements Mover, Sightable {

    public static final int SHOOTING_TIMEOUT = 200;
    private static final boolean DEBUG = true;
    private static final float SPEED = 2.5f;
    private static final int SIZE = 15;
    private final BulletPool bulletPool;
    private final CanMove canMove;

//    private Shape shape;
    private int hmove = 0;
    private int vmove = 0;
    private boolean shooting = false;
    private int shootingTimeout;
    private Vector2f position = new Vector2f();
    private Vector2f target=new Vector2f();

    public Shooter(GameContainer container, int x, int y) {
        this(container, x, y, null, null, null);
    }

    public Shooter(GameContainer gc, int x, int y, final BulletPool bulletPool, CanMove canMove, final RightClickHandler rightClickHandler) {
        this.bulletPool = bulletPool;
        this.canMove = canMove;
        moveTo(x, y);
        moveTarget(x,y);

        gc.getInput().addListener(new InputAdapter() {
            @Override
            public void keyPressed(int key, char c) {
                System.out.println("keyPressed = " + key);
                switch (key) {
                    case Input.KEY_LEFT:
                        hmove = -1;
                        break;
                    case Input.KEY_RIGHT:
                        hmove = 1;
                        break;
                    case Input.KEY_UP:
                        vmove = -1;
                        break;
                    case Input.KEY_DOWN:
                        vmove = 1;
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void keyReleased(int key, char c) {
                System.out.println("keyReleased = " + key);
                switch (key) {
                    case Input.KEY_LEFT:
                    case Input.KEY_RIGHT:
                        hmove = 0;
                        break;
                    case Input.KEY_UP:
                    case Input.KEY_DOWN:
                        vmove = 0;
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void mousePressed(int button, int x, int y) {
                if (button == 0) {
                    shooting = true;
                    resetShootimngTimeout();
                }
                if (button == 1 && rightClickHandler != null) rightClickHandler.mousePressed(x, y);
            }

            @Override
            public void mouseReleased(int button, int x, int y) {
                if (button == 0) {
                    shooting = false;
                    shootingTimeout = 0;
                }
                if (button == 1 && rightClickHandler != null) rightClickHandler.mouseReleased(x, y);
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

    private void moveTarget(float newx, float newy) {
        target.set(newx, newy);
    }

    private void resetShootimngTimeout() {
        shootingTimeout = SHOOTING_TIMEOUT;
    }

    private void shoot() {
        if (bulletPool == null) return;
        Bullet instance = bulletPool.getInstance();
        if (instance != null) {
            instance.spawn(position.getX(), position.getY(),
                    AngleUtils.getTargetAngle(position.getX(), position.getY(), target.getX(), target.getY()));
        }
    }

    private void moveTo(float x, float y) {
        position.set(x, y);
    }

    public void update(GameContainer gc, int delta) {
        float offset = SPEED /** delta*/;
        float newX = position.getX() + hmove * offset;
        float newY = position.getY() + vmove * offset;
        if (canMove(gc, newX, newY)) {
            moveTo(position.getX() + hmove * offset,
                    position.getY() + vmove * offset);
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
        return newX >= 0 && (newX + SIZE) <= gc.getWidth() - 1
                && newY >= 0 && (newY + SIZE) <= gc.getHeight() - 1
                && (canMove == null || canMove.canMove((int) newX, (int) newY));
    }

    public void render(GameContainer gc, Graphics g) {
        g.setColor(Color.red);
        g.drawRect(position.x - SIZE / 2, position.y - SIZE/2, SIZE, SIZE);
        g.setColor(Color.yellow);
        g.drawLine(position.getX(), position.getY(), target.getX(), target.getY());

        if (DEBUG) {
            g.setColor(Color.magenta);
            g.drawString(String.format("dist: %d", calcDistance()), target.getX() + 10, target.getY());
        }
    }

    private int calcDistance() {
        float dx = target.getX() - position.getX();
        float dy = target.getY() - position.getY();
        return (int) Math.sqrt(dx*dx + dy*dy);
    }

    public Vector2f getPosition() {
        return position;
    }
}
