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

public class CheckRoyalFlushTest {
    Player player;
    CheckRoyalFlush checkRoyalFlush;

    @Before
    public void init(){
        player = new Player("Test", 1);
        checkRoyalFlush = new CheckRoyalFlush();
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.ACE, Suit.CLUBS));
        cards.add(new Card(Rank.KING,Suit.SPADES));
        cards.add(new Card(Rank.QUEEN,Suit.HEARTS));
        cards.add(new Card(Rank.TEN,Suit.CLUBS));
        cards.add(new Card(Rank.NIGHT,Suit.DIAMONDS));
        player.setCards(cards);
    }
    @Test
    public void checkTest(){
        checkRoyalFlush.check(player);
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
        Assert.assertFalse(checkRoyalFlush.check(player));
    }
}
