package com.agh.game.handRanking;

import com.agh.game.util.card.Card;
import com.agh.game.util.card.Rank;
import com.agh.game.util.card.Suit;
import com.agh.player.Player;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;


@RunWith(JUnit4.class)
public class CheckFlushTest {

    Player player;
    CheckFlush checkFlush;

    @Before
    public void init(){
        player = new Player("Test", 1);
        checkFlush = new CheckFlush();
        List<Card> cards = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            cards.add(new Card(Rank.values()[i + 2], Suit.CLUBS));
        }
        player.setCards(cards);
    }
    @Test
    public void checkTest(){
        checkFlush.check(player);
    }


}
