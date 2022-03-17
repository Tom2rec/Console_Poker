package com.agh.game.handRanking;

import com.agh.game.util.card.Rank;
import com.agh.player.Player;

/**
 * Class to test high card
 */
public class CheckHighCard extends CheckKindOfHandRating{

    public static final HandRanking HAND_RANKING = HandRanking.HIGH_CARD;

    @Override
    public boolean check(Player player) {
        var highest = player.getCards().get(4).getRank();
        setHeighestRank(highest);
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
