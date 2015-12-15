package org.xflash.collision;

import org.newdawn.slick.geom.Shape;

/**
 */
public interface Collidable {
    boolean collideWith(Shape shape);

    void handleCollision();
}
