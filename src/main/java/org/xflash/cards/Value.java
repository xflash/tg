package org.xflash.cards;

/**
 */
public enum Value {
    UN("1"), DEUX("2"), TROIS("3"), QUATRE("4"),
    CINQ("5"), SIX("6"), SEPT("7"), HUIT("8"), NEUF("9"), DIX("10"),
    VALET("V"), DAME("D"), ROI("R");

    private String shortName;

    Value(String s) {
        shortName = s;
    }

    public String shortName() {
        return shortName;
    }
}
