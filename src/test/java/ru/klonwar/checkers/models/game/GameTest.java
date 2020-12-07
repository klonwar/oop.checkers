package ru.klonwar.checkers.models.game;

import org.junit.Assert;
import org.junit.Test;
import ru.klonwar.checkers.models.database.*;
import ru.klonwar.checkers.mocks.MockUsers;
import ru.klonwar.checkers.models.p2p.ConnectionState;
import ru.klonwar.checkers.models.p2p.SocketCommunicator;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class GameTest {
    private CheckersDatabase testDb = new CheckersDatabase() {
        @Override
        public QueryResponse addUser(User user) {
            return null;
        }

        @Override
        public QueryResponse addGame(Game game) {
            return null;
        }

        @Override
        public List<GameInfo> getGamesInfoForUserID(int userID) {
            return null;
        }

        @Override
        public User getUserByLoginAndPassword(String login, String password) {
            return null;
        }
    };

    @Test
    public void gameInitializesCorrectly() throws IOException {
        ConnectionState connectionState = new ConnectionState(MockUsers.USER_1, MockUsers.USER_2);

        ServerSocket ss = new ServerSocket(3002);
        Socket s1 = new Socket("localhost", 3002);
        Socket s2 = ss.accept();

        connectionState.setSc(new SocketCommunicator(s2));

        System.out.println("ok");

        Game game = new Game(connectionState, testDb);
        System.out.println(game.getField());
    }

}
