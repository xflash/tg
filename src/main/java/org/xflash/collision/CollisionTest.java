package org.xflash.collision;

import org.newdawn.slick.*;

import java.util.ArrayList;

/**
 */
public class CollisionTest extends BasicGame {

    private Shooter shooter;
    private Target target;
    private BulletPool bulletPool;
    private ArrayList<Collidable> collidables;

    public CollisionTest() {
        super("Collision");
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        collidables = new ArrayList<Collidable>();
        bulletPool = new BulletPool(100, collidables);
        shooter = new Shooter(gc, 100, 400, bulletPool);
        target = new Target(gc, 300, 200);
        collidables.add(target);
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        shooter.update(gc, delta);
        target.update(gc, delta);
        bulletPool.update(gc, delta);
    }

    public void render(GameContainer gc, Graphics g) throws SlickException {
        g.setColor(Color.cyan);
        g.drawRect(0, 0, gc.getWidth() - 1, gc.getHeight() - 1);
        shooter.render(gc, g);
        target.render(gc, g);
        bulletPool.render(gc, g);
    }
}
