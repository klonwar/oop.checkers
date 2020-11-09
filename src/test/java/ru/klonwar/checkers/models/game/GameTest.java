package ru.klonwar.checkers.models.game;

import org.junit.Assert;
import org.junit.Test;
import ru.klonwar.checkers.models.game.Cell;
import ru.klonwar.checkers.models.game.Field;
import ru.klonwar.checkers.models.game.Game;
import ru.klonwar.checkers.models.game.Player;

public class GameTest {
    @Test
    public void switchingCorrectly() {
        Game game = new Game();
        Player first = game.getActivePlayer();

        game.switchPlayer();
        Assert.assertNotEquals(first, game.getActivePlayer());

        game.switchPlayer();
        Assert.assertEquals(first, game.getActivePlayer());
    }

    @Test
    public void winnerDetectingCorrectly() {
        Game game = new Game();
        Field field = game.getField();
        Player first = game.getActivePlayer();

        for (Cell[] row : field.getFieldState()) {
            for (Cell item : row) {
                if (item.getChecker() != null && item.getChecker().getColor() == first.getColor()) {
                    item.setChecker(null);
                }
            }
        }

        game.checkWinner();
        Assert.assertTrue(game.haveWinner());
        Assert.assertNotEquals(first, game.getWinner());
    }


}
