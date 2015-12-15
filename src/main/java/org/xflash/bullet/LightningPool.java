package org.xflash.bullet;

import org.xflash.utils.ActorPool;

/**
 */
public class LightningPool extends ActorPool<Lightning> {
    public LightningPool(int i) {
        super(i, Lightning.class);
    }
}
