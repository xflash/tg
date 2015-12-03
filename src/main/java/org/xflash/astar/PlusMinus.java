package org.xflash.astar;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.GUIContext;

/**
 */
public class PlusMinus {
    public static final int SPACE = 5;
    private final ClickableBox b2;
    private final ClickableBox b1;
    public static final int DEFAULT_SIZE = 20;
    
    private int value;

    public int getX() {
        return b1.getX();
    }

    public int getY() {
        return b1.getY();
    }

    public int getWidth() {
        return b1.getWidth()+SPACE+b2.getWidth();
    }

    public PlusMinus(GUIContext context, int x, int y, int v, final ValueListener valueListener) {
        this.value = v;
        b1 = new ClickableBox(context, "-", x, y);
        b1.addListener(new ComponentListener() {
            public void componentActivated(AbstractComponent source) {
                value -= 5;
                valueListener.valueChanged(value);
            }
        });
        x+=b1.getWidth()+SPACE;
        b2 = new ClickableBox(context, "+", x, y);
        b2.addListener(new ComponentListener() {
            public void componentActivated(AbstractComponent source) {
                value += 5;
                valueListener.valueChanged(value);
            }
        });

    }

    public void render(GUIContext container, Graphics gfx) throws SlickException {
        b1.render(container, gfx);
        b2.render(container, gfx);
    }

    public int getValue() {
        return value;
    }
}
