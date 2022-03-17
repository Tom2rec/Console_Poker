package com.agh.game;

import com.agh.player.Player;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class GameTest {

    @Test
    public void setNextPlayerTest(){
        Game game = new Game();
        Player p1 = new Player("ok1", 1);
        p1.setInGame(true);
        Player p2 = new Player("ok2", 2);
        p2.setInGame(true);
        Player p3 = new Player("ok3", 3);
        p3.setInGame(true);
        game.addPlayer(1,p1);
        game.addPlayer(2,p2);
        game.addPlayer(3,p3);

        for(int i = 0; i < 6; i++){
            Assert.assertEquals(i % 3 + 1, game.getCurrentPlayer().getId());
            game.setNextPlayer();
        }

    }
}
