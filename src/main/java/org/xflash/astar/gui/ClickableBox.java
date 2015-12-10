package org.xflash.astar.gui;

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
    private boolean clicked;
    private Rectangle shdow;

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
        gfx.setColor(Color.lightGray);
        gfx.fill(shdow);

        gfx.setColor(bgColor);
        Rectangle shape = clicked ? shdow : box;
        gfx.fill(shape);
        gfx.setColor(borderColor);
        gfx.draw(shape);
        
        if (str != null)
            gfx.drawString(str, shape.getX() + font.getWidth(str) / 2, shape.getY());
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
        shdow = new Rectangle(box.getX() + 1, box.getY() + 1, box.getWidth(), box.getHeight());
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
    public void mouseReleased(int button, int xm, int ym) {
//        if (Rectangle.contains(xm, ym, getX(), getY(), getWidth() - 1, getHeight() - 1)) {
            clicked = false;
//        }
    }

    @Override
    public void mouseDragged(int oldx, int oldy, int newx, int newy) {
        super.mouseDragged(oldx, oldy, newx, newy);
        System.out.println("mouseDragged = " + oldx);
    }

    @Override
    public void mousePressed(int button, int xm, int ym) {
        if (Rectangle.contains(xm, ym, getX(), getY(), getWidth() - 1, getHeight() - 1)) {
            System.out.println("mousePressed = " + button);
            clicked=true;
            notifyListeners();
        }
    }
}
