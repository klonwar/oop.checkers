package ru.klonwar.checkers.models.game.server;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ru.klonwar.checkers.mocks.MockUsers;
import ru.klonwar.checkers.models.database.CheckersDatabase;
import ru.klonwar.checkers.models.game.GameMechanics;
import ru.klonwar.checkers.models.game.Player;
import ru.klonwar.checkers.models.game.PlayerColor;
import ru.klonwar.checkers.models.p2p.GameWithDatabase;

public class GameWithDatabaseTest {
    private CheckersDatabase testDb = Mockito.mock(CheckersDatabase.class);

    private GameWithDatabase gameWithDatabase;

    @Before
    public void initObjects() {
        gameWithDatabase = new GameWithDatabase(testDb);
        gameWithDatabase.setMechanics(new GameMechanics(
                new Player(MockUsers.USER_1, PlayerColor.WHITE),
                new Player(MockUsers.USER_2, PlayerColor.BLACK)
        ));
    }

    @Test
    public void disablingIsCorrect() {
        gameWithDatabase.disable();
        Assert.assertFalse(gameWithDatabase.isEnabled());
        Assert.assertEquals(gameWithDatabase.getMechanics().getActivePlayer().getAvailableToClickCells().size(), 0);
    }

    @Test
    public void enablingIsCorrect() {
        gameWithDatabase.enable();
        Assert.assertTrue(gameWithDatabase.isEnabled());
        Assert.assertNotEquals(gameWithDatabase.getMechanics().getActivePlayer().getAvailableToClickCells().size(), 0);

    }

    @Test
    public void doubleEnablingIsSafe() {

    }

}
