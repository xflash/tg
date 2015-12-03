package org.xflash.astar;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
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

    private static class ClickableBox extends AbstractComponent {

        private final String str;
        private Rectangle box;

        public ClickableBox(GUIContext container, String s) {
            this(container, s, 0, 0);
        }

        public ClickableBox(GUIContext context, String str, int x, int y) {
            super(context);
            this.str = str;
            setLocation(x, y);
        }

        @Override
        public void render(GUIContext container, Graphics gfx) throws SlickException {
            Font font = gfx.getFont();
            gfx.setColor(Color.orange);
            gfx.draw(box);
            gfx.drawString(str, box.getX() + font.getWidth(str) / 2, box.getY());

        }

        @Override
        public void setLocation(int x, int y) {
            box = new Rectangle(x, y, DEFAULT_SIZE, DEFAULT_SIZE);
        }

        @Override
        public int getX() {
            return (int) box.getX();
        }

        @Override
        public int getY() {
            return (int) box.getY();
        }

        @Override
        public int getWidth() { return (int) box.getWidth(); } 
        @Override
        public int getHeight() { return (int) box.getHeight(); }

        @Override
        public void mousePressed(int button, int xm, int ym) {
            if(Rectangle.contains(xm, ym, getX(), getY(), getWidth() - 1, getHeight() - 1)) {
                System.out.println("mousePressed on " + str);
                notifyListeners();
            }
        }
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
