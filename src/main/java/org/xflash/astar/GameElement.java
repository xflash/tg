package org.xflash.astar;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.gui.GUIContext;

/**
 */
public interface GameElement {
    void update(GUIContext container, int delta);

    void render(GUIContext gc, Graphics gfx);
}
