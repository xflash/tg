package org.xflash.cards;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.GUIContext;

/**
 */
public class ClickableStack extends AbstractComponent {
    private Rectangle box;
    private Color bgColor = Color.lightGray;
    private Color borderColor = Color.orange;
    private boolean clicked;
    private Rectangle shdow;

    public ClickableStack(GameContainer container, int x, int y) {
        super(container);
        setLocation(x, y);
    }

    @Override
    public void render(GUIContext container, Graphics gfx) throws SlickException {
        gfx.setColor(Color.lightGray);
        gfx.fill(shdow);

        gfx.setColor(bgColor);
        Rectangle shape = clicked ? shdow : box;
        gfx.fill(shape);
        gfx.setColor(borderColor);
        gfx.draw(shape);
    }

    public void setLocation(int x, int y) {

        box = new Rectangle(x, y, 57, 88);
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
            clicked = true;
            notifyListeners();
        }
    }

}
