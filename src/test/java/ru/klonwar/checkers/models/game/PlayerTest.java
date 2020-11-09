package ru.klonwar.checkers.models.game;

import org.junit.Assert;
import org.junit.Test;
import ru.klonwar.checkers.helpers.Position;
import ru.klonwar.checkers.models.game.Checker;
import ru.klonwar.checkers.models.game.Field;
import ru.klonwar.checkers.models.game.Player;

public class PlayerTest {
    @Test
    public void playerMoveCheckerCorrectly() {
        Field field = new Field();
        Player player0 = new Player(field, 0);
        Player player1 = new Player(field, 1);

        Checker checker0;
        Checker checker1;

        /*
         * Ходит
         * */

        checker0 = field.getCellFromPosition(new Position(1, 2)).getChecker();
        player0.moveChecker(new Position(1, 2), new Position(0, 3));
        Assert.assertEquals(checker0, field.getCellFromPosition(new Position(0, 3)).getChecker());

        checker0 = field.getCellFromPosition(new Position(0, 3)).getChecker();
        player0.moveChecker(new Position(0, 3), new Position(1, 4));
        Assert.assertEquals(checker0, field.getCellFromPosition(new Position(1, 4)).getChecker());


        /*
        * Бьет
        * */

        checker1 = field.getCellFromPosition(new Position(2, 5)).getChecker();
        player1.moveChecker(new Position(2, 5), new Position(0, 3));
        Assert.assertEquals(checker1, field.getCellFromPosition(new Position(0, 3)).getChecker());
        Assert.assertNull(field.getCellFromPosition(new Position(1, 4)).getChecker());

    }

}
