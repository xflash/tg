package org.xflash.cards;

import org.newdawn.slick.*;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;

/**
 */
public class CardsGame extends BasicGame {

    private CardSet cardset;
    private ClickableStack stack;
    private CardSlot cardSlot;

    public CardsGame() {
        super("Title");
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        cardset = CardSet.newClassic();

        cardSlot = new CardSlot(container, 10, 200);

        stack = new ClickableStack(container, 100, 30);
        stack.addListener(new ComponentListener() {
            public void componentActivated(AbstractComponent source) {
                Card card = cardset.pop();
                if (card != null) {
                    cardSlot.set(card);
                }
            }
        });
//        new CardSlot(30, 200);
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {

    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        stack.render(container, g);
        int nb = cardset.size();
        g.drawString(String.format("nb: %d", nb), 100, 30 - g.getFont().getLineHeight());
        cardSlot.render(container, g);
    }
}
