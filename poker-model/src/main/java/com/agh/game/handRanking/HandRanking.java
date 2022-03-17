package com.agh.game.handRanking;

public enum HandRanking {
    RESIGNED("FOLD", -1), HIGH_CARD("HIGH CARD", 0), ONE_PAIR("ONE PAIR", 1), TWO_PAIR("TWO_PAIR", 2),
    THREE_OF_KIND("THREE_OF_KIND", 3), STRAIGHT("STRAIGHT", 4), FLUSH("FLUSH", 5),
    FULL_HOUSE("FULL_HOUSE", 6), QUADS("QUADS", 7), STRAIGHT_FLUSH("STRAIGHT_FLUSH", 8),
    ROYAL_FLUSH("ROYAL_FLUSH", 9);

    public String name;
    public int rate;

    HandRanking(String name, int rate) {
        this.name = name;
        this.rate = rate;
    }
}
