package org.xflash.bullet;

import org.xflash.utils.ActorPool;

/**
 */
public class BulletActorPool extends ActorPool<BulletActor> {

    public BulletActorPool(int n) {
        this(n, null);
    }

    public BulletActorPool(int n, Object[] args) {
        super(n, BulletActor.class, args);
    }

}
