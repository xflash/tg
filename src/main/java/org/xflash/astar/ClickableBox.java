package org.xflash.astar;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.GUIContext;

/**
 */
public class ClickableBox extends AbstractComponent {

    private final String str;
    private Rectangle box;
    private Color bgColor = Color.lightGray;
    private Color borderColor = Color.orange;

    public ClickableBox(GUIContext container, String s) {
        this(container, s, 0, 0);
    }

    public ClickableBox(GUIContext context, String str, int x, int y) {
        super(context);
        this.str = str;
        setLocation(x, y);
    }

    @Override
    public void render(GUIContext container, Graphics gfx)  {
        Font font = gfx.getFont();
        gfx.setColor(bgColor);
        gfx.fill(box);
        gfx.setColor(borderColor);
        gfx.draw(box);
        
        if (str != null)
            gfx.drawString(str, box.getX() + font.getWidth(str) / 2, box.getY());
    }

    public ClickableBox setBgColor(Color bgColor) {
        this.bgColor = bgColor;
        return this;
    }

    public ClickableBox setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
        return this;
    }

    @Override
    public void setLocation(int x, int y) {
        box = new Rectangle(x, y, PlusMinus.DEFAULT_SIZE, PlusMinus.DEFAULT_SIZE);
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
    public int getWidth() {
        return (int) box.getWidth();
    }

    @Override
    public int getHeight() {
        return (int) box.getHeight();
    }

    @Override
    public void mousePressed(int button, int xm, int ym) {
        if (Rectangle.contains(xm, ym, getX(), getY(), getWidth() - 1, getHeight() - 1)) {
            notifyListeners();
        }
    }
}
