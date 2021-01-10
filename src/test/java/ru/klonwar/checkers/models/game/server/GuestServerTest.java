package ru.klonwar.checkers.models.game.server;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ru.klonwar.checkers.models.database.CheckersDatabase;
import ru.klonwar.checkers.models.game.PlayerColor;
import ru.klonwar.checkers.models.p2p.ConnectionState;
import ru.klonwar.checkers.models.p2p.SocketCommunicator;

public class GuestServerTest {
    private CheckersDatabase mockDatabase = Mockito.mock(CheckersDatabase.class);
    private ConnectionState connectionState = new ConnectionState();
    private SocketCommunicator mockCommunicator = Mockito.mock(SocketCommunicator.class);

    private SocketServer gameServer;

    @Before
    public void initObjects() {
        connectionState.setSc(mockCommunicator);
        gameServer = new GuestServer(connectionState, mockDatabase);
    }

    @Test
    public void behaviorAfterConnectIsCorrectIfWhite() {
        Mockito.when(mockCommunicator.read()).thenReturn("0");
        gameServer.connect();

        Mockito.verify(mockCommunicator).send(Mockito.eq("ok"));

        Assert.assertEquals(gameServer.getMechanics().getCurrentPlayerColor(), PlayerColor.WHITE);
        Assert.assertTrue(gameServer.isEnabled());
    }

    @Test
    public void behaviorAfterConnectIsCorrectIfBlack() {
        Mockito.when(mockCommunicator.read()).thenReturn("1");
        gameServer.connect();

        Mockito.verify(mockCommunicator).send(Mockito.eq("ok"));

        Assert.assertEquals(gameServer.getMechanics().getCurrentPlayerColor(), PlayerColor.BLACK);
        Assert.assertFalse(gameServer.isEnabled());
    }
}
