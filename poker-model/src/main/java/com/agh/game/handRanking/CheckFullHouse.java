package com.agh.game.handRanking;

import com.agh.game.util.card.Card;
import com.agh.game.util.card.Rank;
import com.agh.player.Player;

import java.util.List;
/**
 * Class to test fullHouse
 */
public class CheckFullHouse extends CheckKindOfHandRating{

    public static final HandRanking HAND_RANKING = HandRanking.FULL_HOUSE;

    @Override
    public boolean check(Player player) {
        List<Card> hand = player.getCards();
        int i;
        var threeFounded = false;
        for(i = 2; i >= 0; i--){
            var current = hand.get(i).getRank();
            if(hand.get(i+1).getRank().equals(current) && hand.get(i+2).getRank().equals(current)){
                setHeighestRank(current);
                threeFounded = true;
                break;
            }
        }

//        in sorted hand first and last card cannot be pair
        if(i == 1) {
            return false;
        }

        int first = (i + 3) % 5;
        int second = (i + 4) % 5;

        // three card with same rank was founded and remaining ones create pair
        return threeFounded && hand.get(first).getRank().equals(hand.get(second).getRank());



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
