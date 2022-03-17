package com.agh.game.handRanking;

import com.agh.player.Player;

public interface ICheckKindOfHandRating {

    boolean check(Player player);

    int getRate();
}
