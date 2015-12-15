package org.xflash.collision;

import org.xflash.utils.ActorPool;

import java.util.List;

/**
 */
public class BulletPool extends ActorPool<Bullet> {
    public BulletPool(int n, List<Collidable> collidables) {
        super(n, Bullet.class, new Object[]{collidables});
    }
}
