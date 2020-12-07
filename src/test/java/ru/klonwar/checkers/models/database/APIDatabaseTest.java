package ru.klonwar.checkers.models.database;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.klonwar.checkers.mocks.MockUsers;
import ru.klonwar.checkers.models.game.Game;
import ru.klonwar.checkers.models.p2p.ConnectionState;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

public class APIDatabaseTest {
    APIDatabase testDb;

    @Before
    public void initObjects() {
        try {
            Properties p = new Properties();
            p.load(new FileInputStream("./app.properties"));
            String testXmlLink = p.get("api-test-link").toString();

            testDb = new APIDatabase(testXmlLink);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void addingUserWorksCorrectly() {
        QueryResponse res = testDb.addUser(MockUsers.USER_1);
        boolean isCorrect = res.getMessage().equals("Успешно") || res.getMessage().equals("Такой пользователь уже зарегистрирован");
        Assert.assertTrue(isCorrect);
    }
    @Test
    public void addingGameWorksCorrectly() {
        Game game = new Game(new ConnectionState(MockUsers.USER_1, MockUsers.USER_2), testDb);
        QueryResponse qr = game.finish(1);
        System.out.println(qr.getMessage());
        Assert.assertTrue(qr.isSuccessful());
    }
    @Test
    public void gettingUserWorksCorrectly() {
        User user = testDb.getUserByLoginAndPassword(MockUsers.USER_2.getLogin(), MockUsers.USER_2.getPassword());
        Assert.assertNotNull(user);
    }

    @Test
    public void gettingGamesWorksCorrectly() {
        List<GameInfo> user = testDb.getGamesInfoForUserID(MockUsers.USER_2.getId());
        Assert.assertNotNull(user);
    }

}
