package org.xflash.steering;

import org.lwjgl.util.vector.Vector2f;

/**
 */
public interface Steering {
    Vector2f computeTarget(Vector2f position);
}
