package org.xflash.collision;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.xflash.utils.ActorPool;

import java.util.List;

/**
 */
public class BulletPool extends ActorPool<Bullet> {
    public BulletPool(int n, List<Collidable> collidables) {
        super(n, Bullet.class, new Object[]{collidables});
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        super.render(gc, g);
        g.setColor(Color.white);
        g.drawString(String.format("Bullets :%d/%d", living(), actor.length), 200, 10);
    }

}
