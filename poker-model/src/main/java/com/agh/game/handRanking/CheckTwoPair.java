package com.agh.game.handRanking;

import com.agh.game.util.card.Card;
import com.agh.game.util.card.Rank;
import com.agh.player.Player;

import java.util.List;

/**
 * class to test two pair
 */
public class CheckTwoPair extends CheckKindOfHandRating{

    public static final HandRanking HAND_RANKING = HandRanking.TWO_PAIR;

    @Override
    public boolean check(Player player) {
        List<Card> hand = player.getCards();
        var pairs = 0;
        for(var i = 3; i >= 0; i--){
            var current = hand.get(i).getRank();
            if(hand.get(i+1).getRank().equals(current)){
                setHeighestRank(current);
                pairs++;
                i--;
            }
        }
        return pairs == 2;

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
