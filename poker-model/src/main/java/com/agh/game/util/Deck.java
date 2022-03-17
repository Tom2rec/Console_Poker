package com.agh.game.util;

import com.agh.game.util.card.Card;
import com.agh.game.util.card.Rank;
import com.agh.game.util.card.Suit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class to represent Deck of card
 *
 * @author Tom
 * @version 1.0.0
 * @since 20.10.2021
 */

public class Deck {
    /**
     * deck of cards
     */
    private List<Card> deck = new ArrayList<>();

    public Deck() {
        deck = new ArrayList<>();
        for (Rank rank : Rank.values()) {
            for (Suit suit : Suit.values()) {
                deck.add(new Card(rank, suit));
            }
        }
    }

    public List<Card> takeNCard(int n) {
        List<Card> cards = new ArrayList<>();
        for (var i = 0; i < n; i++) {
            cards.add(takeOne());
        }
        return cards;
    }

    public Card takeOne() {
        return deck.remove(0);
    }

    /**
     * Shuffles the deck of card - creating random order
     */
    public void shuffle() {
        Collections.shuffle(deck);
    }

    /**
     * @return deck with cards
     * @since 1.0.0
     */
    public List<Card> getDeck() {
        return deck;
    }

    /**
     * @return string representation of deck
     * @since 1.0.0
     */
    @Override
    public String toString() {
        return "Deck{" +
                "deck=" + deck +
                '}';
    }
}
