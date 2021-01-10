package ru.klonwar.checkers.models.game.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import ru.klonwar.checkers.mocks.MockUsers;
import ru.klonwar.checkers.models.database.CheckersDatabase;
import ru.klonwar.checkers.models.game.Field;
import ru.klonwar.checkers.models.game.GameMechanics;
import ru.klonwar.checkers.models.game.Player;
import ru.klonwar.checkers.models.game.PlayerColor;
import ru.klonwar.checkers.models.p2p.ConnectionState;
import ru.klonwar.checkers.models.p2p.SocketCommunicator;
import ru.klonwar.checkers.util.Position;

import static org.mockito.ArgumentMatchers.any;

public class HostServerTest {
    private CheckersDatabase mockDatabase = Mockito.mock(CheckersDatabase.class);
    private ConnectionState connectionState = new ConnectionState();
    private SocketCommunicator mockCommunicator = Mockito.mock(SocketCommunicator.class);

    private SocketServer gameServer;

    @Before
    public void initObjects() {
        connectionState.setSc(mockCommunicator);
        gameServer = new HostServer(connectionState, mockDatabase);
    }

    @Test
    public void behaviorAfterConnectIsCorrect() {
        PlayerColor cpc = gameServer.getMechanics().getCurrentPlayerColor();
        Mockito.when(mockCommunicator.read()).thenReturn("ok");
        gameServer.connect();

        Mockito.verify(mockCommunicator).send(
                Mockito.eq(cpc.nextColor().ordinal() + "")
        );
        if (cpc == PlayerColor.WHITE)
            Assert.assertTrue(gameServer.isEnabled());
        else
            Assert.assertFalse(gameServer.isEnabled());
    }
}
