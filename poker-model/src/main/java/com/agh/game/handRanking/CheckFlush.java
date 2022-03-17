package com.agh.game.handRanking;

import com.agh.game.util.card.Card;
import com.agh.player.Player;

import java.util.List;

/**
 * Class to test flush
 */
public class CheckFlush extends CheckKindOfHandRating {

    public static final HandRanking HAND_RANKING = HandRanking.FLUSH;

    @Override
    public boolean check(Player player) {
        List<Card> hand = player.getCards();
        for (var i = 3; i >= 0; i--) {
            if (!hand.get(i + 1).getSuit().equals(hand.get(i).getSuit())) {
                return false;
            }
        }
        setHeighestRank(hand.get(4).getRank());
        return true;
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
