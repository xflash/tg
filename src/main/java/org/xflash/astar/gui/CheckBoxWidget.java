package org.xflash.astar.gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.GUIContext;

/**
 */
public class CheckBoxWidget {

    private final AbstractComponent comp;
    private Rectangle cb;
    private boolean value = false;

    public CheckBoxWidget(GUIContext gc, int x, int y, final ValueListener<Boolean> valueListener) {
        comp = new AbstractComponent(gc) {
            @Override
            public void render(GUIContext container, Graphics gfx) throws SlickException {
                gfx.draw(cb);
                if (value) {
                    float lineWidth = gfx.getLineWidth();
                    gfx.setLineWidth(2f);
                    gfx.drawLine(
                            cb.getX() + 1, cb.getY() + 2,
                            cb.getX() + cb.getWidth() - 2, cb.getY() + cb.getHeight() - 2
                    );
                    gfx.drawLine(
                            cb.getX() + 1, cb.getY() + cb.getHeight() - 2,
                            cb.getX() + cb.getWidth() - 2, cb.getY() + 2
                    );
//                    gfx.drawString("X", cb.getX() + gfx.getFont().getWidth("X") / 2, cb.getY());
                    gfx.setLineWidth(lineWidth);
                }
            }

            @Override
            public void setLocation(int x, int y) {
                cb = new Rectangle(x, y, 20, 20);

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
        };
        comp.setLocation(x, y);
    }


    public boolean getValue() {
        return value;
    }

    public void render(GUIContext gc, Graphics gfx) throws SlickException {
        comp.render(gc, gfx);
    }


}
