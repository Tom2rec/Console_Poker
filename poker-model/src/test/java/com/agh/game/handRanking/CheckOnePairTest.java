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

public class CheckOnePairTest {
    Player player;
    CheckOnePair checkOnePair;

    @Before
    public void init(){
        player = new Player("Test", 1);
        checkOnePair = new CheckOnePair();
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.EIGHT, Suit.CLUBS));
        cards.add(new Card(Rank.NIGHT,Suit.SPADES));
        cards.add(new Card(Rank.TEN,Suit.HEARTS));
        cards.add(new Card(Rank.SEVEN,Suit.CLUBS));
        cards.add(new Card(Rank.SEVEN,Suit.HEARTS));
        player.setCards(cards);
    }
    @Test
    public void checkTest(){
        checkOnePair.check(player);
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
        Assert.assertFalse(checkOnePair.check(player));
    }
}
