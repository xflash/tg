package org.xflash.cards;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.GUIContext;
import org.xflash.astar.gui.ClickableBox;

/**
 */
public class CardSlot extends AbstractComponent {


    private Card card;

    private Color bgColor = Color.lightGray;
    private Color borderColor = Color.orange;


    private Rectangle box;

    public CardSlot(GameContainer container, int x, int y) {
        super(container);
        setLocation(x, y);

    }

    public void set(Card card) {
        this.card = card;
    }

    @Override
    public void render(GUIContext container, Graphics gfx) throws SlickException {

        Rectangle shape = box;
//        gfx.setColor(bgColor);
//        gfx.fill(shape);
        gfx.setColor(borderColor);
        gfx.draw(shape);
        if (card != null) {
            card.render(gfx, box.getX(), box.getY());
        }
    }

    @Override
    public void setLocation(int x, int y) {
        box = new Rectangle(x, y, 57, 88);
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

//    @Override
//    public void mouseReleased(int button, int xm, int ym) {
////        if (Rectangle.contains(xm, ym, getX(), getY(), getWidth() - 1, getHeight() - 1)) {
//        clicked = false;
////        }
//    }
//
//    @Override
//    public void mouseDragged(int oldx, int oldy, int newx, int newy) {
//        super.mouseDragged(oldx, oldy, newx, newy);
//        System.out.println("mouseDragged = " + oldx);
//    }

    @Override
    public void mousePressed(int button, int xm, int ym) {
        if (Rectangle.contains(xm, ym, getX(), getY(), getWidth() - 1, getHeight() - 1)) {
            System.out.println("mousePressed = " + button);
//            clicked=true;
            notifyListeners();
        }
    }
}
