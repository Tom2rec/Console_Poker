package com.agh.game.util.card;

import java.util.Objects;

/**
 * Class to represent instance of playing card
 * @author Tom
 * @version 1.0.0
 */
public class Card{
    /**
     * Rank of card from ACE to KING
     */
    private Rank rank;
    /**
     * Suit of card from HEART to SPADE
     */
    private Suit suit;

    /**
     * Constructor for card
     * @param rank
     * @param suit
     * @since 1.0.0
     */
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    /**
     *
     * @return rank of card
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * Sets rank for card
     * @param rank
     */
    public void setRank(Rank rank) {
        this.rank = rank;
    }

    /**
     *
     * @return string representation of card
     */
    @Override
    public String toString() {
        return "Card{" +
                "rank=" + rank +
                ", suit=" + suit +
                '}';
    }

    /**
     *
     * @param o
     * @return if object are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var card = (Card) o;
        return rank == card.rank && suit == card.suit;
    }

    /**
     *
     * @return hashCode for card
     */
    @Override
    public int hashCode() {
        return Objects.hash(rank, suit);
    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }
}
