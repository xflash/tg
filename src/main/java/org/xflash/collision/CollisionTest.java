package org.xflash.collision;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 */
public class CollisionTest extends BasicGame {

    private Shooter shooter;
    private Target target;
    private BulletPool bulletPool;

    public CollisionTest() {
        super("Collision");
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        bulletPool = new BulletPool(100);
        shooter = new Shooter(gc, 100, 400, bulletPool);
        target = new Target(gc, 300, 200);
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        shooter.update(gc, delta);
        target.update(gc, delta);
        bulletPool.update(gc, delta);
    }

    public void render(GameContainer gc, Graphics g) throws SlickException {
        shooter.render(gc, g);
        target.render(gc, g);
        bulletPool.render(gc, g);
    }
}
