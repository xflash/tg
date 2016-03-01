package org.xflash.cards;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

/**
 */
public class Card {

    private final Rectangle card;
    private final Value val;
    private final Family family;

    public Card(Value val, Family family) {
        this.val = val;
        this.family = family;
        card = new Rectangle(0, 0, 57, 88);
    }

    public void render(Graphics gfx, float x, float y) {
        gfx.translate(x, y);
        gfx.setColor(Color.lightGray);
        gfx.fill(card);
        gfx.setColor(Color.blue);
        String str = String.format("%s %s", val.shortName(), family.name().substring(0, 1));
        Font gfxFont = gfx.getFont();
        gfx.drawString(str,
                card.getCenterX() - gfxFont.getWidth(str) / 2,
                card.getCenterY() - gfxFont.getLineHeight() / 2);
        gfx.resetTransform();
    }
}
