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

public class XMLDatabaseTest {
    XMLDatabase testDb;

    @Before
    public void initObjects() {
        try {
            Properties p = new Properties();
            p.load(new FileInputStream("./app.properties"));
            String testXmlLink = p.get("xml-test-link").toString();

            testDb = new XMLDatabase(testXmlLink);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void addingUserWorksCorrectly() {
        QueryResponse qr = testDb.addUser(MockUsers.USER_1);
        Assert.assertTrue(qr.isSuccessful());

        User user = testDb.getUserByLoginAndPassword(MockUsers.USER_1.getLogin(), MockUsers.USER_1.getPassword());
        Assert.assertNotNull(user);
    }

    @Test
    public void addingGameWorksCorrectly() {
        Game game = new Game(new ConnectionState(MockUsers.USER_1, MockUsers.USER_2), testDb);
        QueryResponse qr = game.finish(1);
        Assert.assertTrue(qr.isSuccessful());

        List<GameInfo> gl =  testDb.getGamesInfoForUserID(MockUsers.USER_1.getId());
        Assert.assertNotEquals(gl.size(), 0);

        gl =  testDb.getGamesInfoForUserID(MockUsers.USER_2.getId());
        Assert.assertNotEquals(gl.size(), 0);
    }
}
