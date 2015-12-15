package org.xflash.bullet;

import org.xflash.utils.ActorPool;

/**
 */
public class EffectPool extends ActorPool<Effect> {
    public EffectPool(int i) {
        super(i, Effect.class);
    }
}
