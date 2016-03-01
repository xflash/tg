package org.xflash.cards;

import java.util.ArrayList;
import java.util.Collections;

/**
 */
public class CardSet {

    private ArrayList<Card> cards = new ArrayList<Card>();

    public static CardSet newClassic() {
        CardSet cardSet = new CardSet();
        for (Family family : Family.values()) {
            for (Value value : Value.values()) {
                cardSet.addCard(new Card(value, family));
            }
        }
        cardSet.shuffle();
        return cardSet;
    }

    private void shuffle() {
        Collections.shuffle(cards);
    }

    private void addCard(Card card) {
        cards.add(card);
    }

    public Card pop() {
        if (cards.isEmpty()) return null;
        return cards.remove(cards.size() - 1);
    }

    public int size() {
        return cards.size();
    }
}
