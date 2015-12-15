package org.xflash.utils;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * Actor in the game that has the interface to move and draw.
 */
public abstract class Actor {
    private boolean _exists;

    public boolean exists() {
        return _exists;
    }

    public boolean exists(boolean value) {
        return _exists = value;
    }

    public abstract void init(Object[] args);

    public abstract void update(GameContainer gc, int delta);

    public abstract void render(GameContainer gc, Graphics g);
}
