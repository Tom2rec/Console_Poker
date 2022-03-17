package com.agh.game.handRanking;

import com.agh.game.util.card.Card;
import com.agh.game.util.card.Rank;
import com.agh.player.Player;

import java.util.List;

/**
 * class to test straight
 */
public class CheckStraight extends CheckKindOfHandRating{

    public static final HandRanking HAND_RANKING = HandRanking.STRAIGHT;

    @Override
    public boolean check(Player player) {
        List<Card> hand = player.getCards();

        for (var i = 2; i >= 0; i--) {
            Card current = hand.get(i);
            if (hand.get(i + 1).getRank().getValue() != (current.getRank().getValue() + 1)) {
                return false;
            }
        }

        // Straight option 10-J-Q-K-A or A-2-3-4-5

        var rankFive = hand.get(4).getRank();
        var rankFour = hand.get(3).getRank();

        if (rankFive == Rank.ACE){
            if( rankFour == Rank.KING){
                setHeighestRank(Rank.ACE);
                return true;
            }

            if(rankFour == Rank.FIVE) {
                setHeighestRank(Rank.FIVE);
                return true;
            }

            return false;
        }


        if( rankFive.getValue() == rankFour.getValue() + 1){
            setHeighestRank(rankFive);
            return true;
        }

        return false;
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
