package org.xflash.collision;

import org.xflash.utils.ActorPool;

/**
 */
public class BulletPool extends ActorPool<Bullet> {
    public BulletPool(int n) {
        super(n, Bullet.class);
    }
}
