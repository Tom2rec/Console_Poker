package com.agh.game.handRanking;

import com.agh.game.util.card.Card;
import com.agh.game.util.card.Rank;
import com.agh.game.util.card.Suit;
import com.agh.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CheckTwoPairTest {
    Player player;
    CheckTwoPair checkTwoPair;

    @Before
    public void init(){
        player = new Player("Test", 1);
        checkTwoPair = new CheckTwoPair();
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.SEVEN, Suit.HEARTS));
        cards.add(new Card(Rank.FIVE,Suit.CLUBS));
        cards.add(new Card(Rank.SIX,Suit.SPADES));
        cards.add(new Card(Rank.SEVEN,Suit.CLUBS));
        cards.add(new Card(Rank.FIVE,Suit.SPADES));
        player.setCards(cards);
    }
    @Test
    public void checkTest(){
        checkTwoPair.check(player);
    }

    @Test
    public void checkError(){
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.TWO,Suit.CLUBS));
        cards.add(new Card(Rank.EIGHT,Suit.SPADES));
        cards.add(new Card(Rank.NIGHT,Suit.HEARTS));
        cards.add(new Card(Rank.FIVE,Suit.CLUBS));
        cards.add(new Card(Rank.SEVEN,Suit.HEARTS));
        player.setCards(cards);
        Assert.assertFalse(checkTwoPair.check(player));
    }
}
