package ru.klonwar.checkers.models.database;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.klonwar.checkers.mocks.MockUsers;
import ru.klonwar.checkers.models.game.Game;
import ru.klonwar.checkers.models.p2p.ConnectionState;

import java.io.FileInputStream;
import java.sql.Connection;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class H2DatabaseTest {
    private H2Database testDb;

    @Before
    public void initObjects() {
        try {
            Properties p = new Properties();
            p.load(new FileInputStream("./app.properties"));
            String testSqlLink = p.get("sql-test-link").toString();

            testDb = new H2Database(testSqlLink);

            try (Connection connection = testDb.getConnection()) {
                connection.prepareStatement(H2Database.USER_TABLE_CREATE_SCRIPT).execute();
                connection.prepareStatement(H2Database.GAME_TABLE_CREATE_SCRIPT).execute();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void userInsertsAndFoundsCorrectly() {
        QueryResponse qr = testDb.addUser(MockUsers.USER_1);
        Assert.assertTrue(qr.isSuccessful());

        qr = testDb.addUser(MockUsers.USER_1);
        Assert.assertFalse(qr.isSuccessful());

        User us = testDb.getUserByLoginAndPassword(MockUsers.USER_1.getLogin(), MockUsers.USER_1.getPassword());
        Assert.assertNotNull(us);
        Assert.assertEquals(us.getLogin(), "mock1");
    }

    @Test
    public void gameInsertsAndFoundsCorrectly() {
        testDb.addUser(MockUsers.USER_1);
        testDb.addUser(MockUsers.USER_2);

        Game game = new Game(new ConnectionState(MockUsers.USER_1, MockUsers.USER_2), testDb);
        QueryResponse qr = game.finish(1);

        Assert.assertTrue(qr.isSuccessful());

        List<GameInfo> games = testDb.getGamesInfoForUserID(MockUsers.USER_1.getId());
        List<GameInfo> thisGame = games.stream().filter((item) -> item.getFinishTime() == game.getFinishTime()).collect(Collectors.toList());

        Assert.assertEquals(thisGame.size(), 1);

        games = testDb.getGamesInfoForUserID(MockUsers.USER_2.getId());
        thisGame = games.stream().filter((item) -> item.getFinishTime() == game.getFinishTime()).collect(Collectors.toList());

        Assert.assertEquals(thisGame.size(), 1);
    }

}
