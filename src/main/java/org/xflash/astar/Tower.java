package org.xflash.astar;

import org.lwjgl.util.Point;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.gui.GUIContext;

/**
 */
public class Tower implements GameElement {
    
    final Point pos;

    public Tower(int x, int y) {
        pos=new Point(x, y);
    }

    public void update(GUIContext container, int delta) {
        
    }

    public void render(GUIContext gc, Graphics gfx) {
        gfx.setColor(Color.orange);
        gfx.drawRect(0,0, 24,24);
    }
}
