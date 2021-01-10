package ru.klonwar.checkers.models.game.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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

public class SocketServerTest {
    private CheckersDatabase mockDatabase = Mockito.mock(CheckersDatabase.class);
    private ConnectionState connectionState = new ConnectionState();
    private SocketCommunicator mockCommunicator = Mockito.mock(SocketCommunicator.class);

    private SocketServer gameServer;

    @Before
    public void initObjects() {
        connectionState.setSc(mockCommunicator);
        gameServer = new SocketServer(connectionState, mockDatabase);
        gameServer.setMechanics(new GameMechanics(
                new Player(MockUsers.USER_1, PlayerColor.WHITE),
                new Player(MockUsers.USER_2, PlayerColor.BLACK)
        ));
    }

    @Test
    public void behaviorAfterConnectIsCorrect() {
        gameServer.connect();
        Assert.assertTrue(gameServer.isEnabled());
    }

    @Test
    public void fieldReadsCorrectly() throws InterruptedException {
        Mockito.when(mockCommunicator.read()).thenReturn("{\"fieldState\":[[{\"checker\":null},{\"checker\":{\"king\":true,\"color\":\"BLACK\"}},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null}],[{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null}],[{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null}],[{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null}],[{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null}],[{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null}],[{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":{\"king\":true,\"color\":\"WHITE\"}}],[{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null}]]}\n");
        gameServer.nextTurn();
        Thread.sleep(1000);

        Assert.assertTrue(gameServer.getMechanics().getField().getCellFromPosition(new Position(0, 1)).getChecker().isKing());
    }

    @Test
    public void fieldSendsCorrectly() throws JsonProcessingException {
        Mockito.when(mockCommunicator.read()).thenReturn("{\"fieldState\":[[{\"checker\":null},{\"checker\":{\"king\":true,\"color\":\"BLACK\"}},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null}],[{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null}],[{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null}],[{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null}],[{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null}],[{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null}],[{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":{\"king\":true,\"color\":\"WHITE\"}}],[{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null}]]}\n");
        gameServer.nextTurn();

        ObjectMapper om = new ObjectMapper();
        String correctRes = "";
        correctRes = om.writeValueAsString(new Field());

        Mockito.verify(mockCommunicator).send(Mockito.eq(correctRes));
    }

    @Test
    public void behaviorAfterPlayerVictory() throws JsonProcessingException {
        Mockito.when(mockCommunicator.read()).thenReturn("{\"fieldState\":[[{\"checker\":null},{\"checker\":{\"king\":true,\"color\":\"BLACK\"}},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null}],[{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null}],[{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null}],[{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null}],[{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null}],[{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null}],[{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":{\"king\":true,\"color\":\"WHITE\"}}],[{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null}]]}\n");
        gameServer.getMechanics().finish(PlayerColor.WHITE);
        gameServer.nextTurn();

        ObjectMapper om = new ObjectMapper();
        String correctRes = "";

        correctRes = om.writeValueAsString(new Field());

        Mockito.verify(mockCommunicator).send(Mockito.eq(correctRes));
        Mockito.verify(mockDatabase).addGame(Mockito.any());
    }

    @Test
    public void behaviorAfterOpponentVictory() {
        Mockito.when(mockCommunicator.read()).thenReturn("{\"fieldState\":[[{\"checker\":null},{\"checker\":{\"king\":true,\"color\":\"BLACK\"}},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null}],[{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null}],[{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null}],[{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null}],[{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null}],[{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null}],[{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":{\"king\":true,\"color\":\"WHITE\"}}],[{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null},{\"checker\":null}]]}\n");
        gameServer.getMechanics().finish(PlayerColor.BLACK);
        gameServer.nextTurn();

        Assert.assertEquals(gameServer.getMechanics().getCurrentPlayerColor(), PlayerColor.BLACK);
    }
}
