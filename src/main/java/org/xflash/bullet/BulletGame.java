package org.xflash.bullet;

import org.newdawn.slick.*;

/**
 */
public class BulletGame extends BasicGame {

    private LightningPool lightningPool;
    private BulletActorPool pool;
    private EffectPool effectPool;

    public BulletGame() {
        super("BulletGame");
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        pool = new BulletActorPool(500);
        effectPool = new EffectPool(100);
        lightningPool = new LightningPool(100);

    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        pool.update(gc, delta);
        effectPool.update(gc, delta);
        lightningPool.update(gc, delta);

    }

    public void render(GameContainer gc, Graphics g) throws SlickException {
        pool.render(gc, g);
        effectPool.render(gc, g);
        lightningPool.render(gc, g);
    }


    @Override
    public void mouseReleased(int button, int x, int y) {

        BulletActor instance = pool.getInstance();
        instance.spawn(x, y);
        addEffect(instance);
    }

    private void addEffect(BulletActor instance) {
//        effectPool.getInstance().spawn(instance.getCenter().getX(), instance.getCenter().getY());
        lightningPool.getInstance().spawn(instance.getCenter().getX(), instance.getCenter().getY(), 200, 20);
    }
}
