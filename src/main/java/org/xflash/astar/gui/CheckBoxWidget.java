package org.xflash.astar.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.GUIContext;

/**
 */
public class CheckBoxWidget extends AbstractComponent {

    private Rectangle cb;
    private boolean value = false;
    private String str;
    private ValueListener<Boolean> valueListener;
    private int size = 20;

    public CheckBoxWidget(GUIContext gc, int x, int y, String str, final ValueListener<Boolean> valueListener) {
        super(gc);
        this.str = str;
        this.valueListener = valueListener;
        setLocation(x, y);
    }

    @Override
    public void setLocation(int x, int y) {
        cb = new Rectangle(x, y, size, size);
    }

    @Override
    public void render(GUIContext container, Graphics gfx) throws SlickException {
        gfx.setColor(Color.lightGray);
        gfx.draw(cb);
        gfx.drawString(str, cb.getX() + cb.getWidth() + 5, cb.getY());

        if (value) {
//            float lineWidth = gfx.getLineWidth();
//            gfx.setLineWidth(2f);
//            gfx.setColor(Color.darkGray);
            gfx.drawLine(
                    cb.getX() + 1, cb.getY() + 2,
                    cb.getX() + cb.getWidth() - 2, cb.getY() + cb.getHeight() - 2
            );
            gfx.drawLine(
                    cb.getX() + 1, cb.getY() + cb.getHeight() - 2,
                    cb.getX() + cb.getWidth() - 2, cb.getY() + 2
            );
//            gfx.setLineWidth(lineWidth);
        }
    }


    @Override
    public int getX() {
        return (int) cb.getX();
    }

    @Override
    public int getY() {
        return (int) cb.getY();
    }

    @Override
    public int getWidth() {
        return (int) cb.getWidth();
    }

    @Override
    public int getHeight() {
        return (int) cb.getHeight();
    }

    @Override
    public void mousePressed(int button, int xm, int ym) {
        if (Rectangle.contains(xm, ym, getX(), getY(), getWidth() - 1, getHeight() - 1)) {
            value = !value;
            valueListener.valueChanged(value);
        }
    }


    public boolean getValue() {
        return value;
    }


}
