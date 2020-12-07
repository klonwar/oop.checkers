package ru.klonwar.checkers.models.game;

import org.junit.Assert;
import org.junit.Test;
import ru.klonwar.checkers.models.database.*;
import ru.klonwar.checkers.mocks.MockUsers;
import ru.klonwar.checkers.models.p2p.ConnectionState;

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
    public void switchingCorrectly() {
        Game game = new Game(new ConnectionState(MockUsers.USER_1, MockUsers.USER_2), testDb);
        Player first = game.getActivePlayer();

        game.switchPlayer();
        Assert.assertNotEquals(first, game.getActivePlayer());

        game.switchPlayer();
        Assert.assertEquals(first, game.getActivePlayer());
    }

    @Test
    public void winnerDetectingCorrectly() {
        Game game = new Game(new ConnectionState(MockUsers.USER_1, MockUsers.USER_2), testDb);
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
