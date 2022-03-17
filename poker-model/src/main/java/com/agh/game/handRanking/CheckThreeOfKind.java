package com.agh.game.handRanking;

import com.agh.game.util.card.Card;
import com.agh.game.util.card.Rank;
import com.agh.player.Player;

import java.util.List;

/**
 * class to test three of kind
 */
public class CheckThreeOfKind extends CheckKindOfHandRating{

    public static final HandRanking HAND_RANKING = HandRanking.THREE_OF_KIND;

    @Override
    public boolean check(Player player) {
        List<Card> hand = player.getCards();
        for(int i = 2; i >= 0; i--){
            var current = hand.get(i).getRank();
            if(hand.get(i+1).getRank().equals(current) && hand.get(i+2).getRank().equals(current)){
                setHeighestRank(current);
                return true;
            }
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
