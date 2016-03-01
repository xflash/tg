package org.xflash.cards;

/**
 */
public enum Family {
    CARREAU("\u2662"), COEUR("\u2661"), TREFLE("\u2663"), PIQUE("\u2660");

    private final String symbol;

    Family(String s) {
        symbol = s;
    }

    public String symbol() {
        return symbol;
    }
}
