package com.agh.game.handRanking;

import com.agh.game.util.card.Card;
import com.agh.game.util.card.Rank;
import com.agh.game.util.card.Suit;
import com.agh.player.Player;

import java.util.List;
/**
 * class to test one royalflush
 */
public class CheckRoyalFlush extends CheckKindOfHandRating {

    public static final HandRanking HAND_RANKING = HandRanking.ROYAL_FLUSH;

    @Override
    public boolean check(Player player) {
        List<Card> hand = player.getCards();
        var firstSuit = hand.get(0).getSuit();
        for(Card card : hand){
            if(!card.getSuit().equals(firstSuit)){
                return false;
            }
        }

        return hand.get(0).getRank().equals(Rank.TEN) &&
                hand.get(1).getRank().equals(Rank.JACK) &&
                hand.get(2).getRank().equals(Rank.QUEEN) &&
                hand.get(3).getRank().equals(Rank.KING) &&
                hand.get(4).getRank().equals(Rank.ACE);
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
