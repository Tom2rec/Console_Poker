package com.agh.game.handRanking;

import com.agh.game.util.card.Card;
import com.agh.player.Player;

import java.util.List;

/**
 * class to test one straight flush
 */
public class CheckStraightFlush extends CheckKindOfHandRating{

    public static final HandRanking HAND_RANKING = HandRanking.STRAIGHT_FLUSH;

    @Override
    public boolean check(Player player) {
        List<Card> hand = player.getCards();
        for (var i = 0; i < 4; i++) {
            if (!hand.get(i).getSuit().equals(hand.get(i + 1).getSuit())) {
                return false;
            }
        }

        var checkStraight = new CheckStraight();
        return checkStraight.check(player);
    }

    @Override
    public int getRate() {
        return HAND_RANKING.rate;
    }

    @Override
    public HandRanking getHandRanking() {
        return HAND_RANKING;
    }
}
