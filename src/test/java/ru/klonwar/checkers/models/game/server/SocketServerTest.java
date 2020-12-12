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
import ru.klonwar.checkers.models.p2p.ConnectionState;
import ru.klonwar.checkers.models.p2p.GameServer;
import ru.klonwar.checkers.models.p2p.SocketCommunicator;

public class SocketServerTest {
    private CheckersDatabase testDb = Mockito.mock(CheckersDatabase.class);
    private ConnectionState connectionState = Mockito.mock(ConnectionState.class);

    private SocketServer gameServer;

    @Before
    public void initObjects() {
        gameServer = new SocketServer(connectionState, testDb);
        gameServer.setMechanics(new GameMechanics(
                new Player(MockUsers.USER_1, PlayerColor.WHITE),
                new Player(MockUsers.USER_2, PlayerColor.BLACK)
        ));
    }

    @Test
    public void disablingIsCorrect() {
        gameServer.disable();
        Assert.assertFalse(gameServer.isEnabled());
        Assert.assertEquals(gameServer.getMechanics().getActivePlayer().getAvailableToClickCells().size(), 0);
    }

    @Test
    public void enablingIsCorrect() {
        gameServer.enable();
        Assert.assertTrue(gameServer.isEnabled());
        Assert.assertNotEquals(gameServer.getMechanics().getActivePlayer().getAvailableToClickCells().size(), 0);

    }

}
