package com.agh.game.handRanking;

import com.agh.game.util.card.Rank;

public abstract class CheckKindOfHandRating implements ICheckKindOfHandRating {

    private Rank heighestRank = Rank.TWO;

    public Rank getHeighestRank() {
        return heighestRank;
    }

    public abstract HandRanking getHandRanking();

    public void setHeighestRank(Rank heighestRank) {
        this.heighestRank = heighestRank;
    }
}
