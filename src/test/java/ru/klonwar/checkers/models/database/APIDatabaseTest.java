package ru.klonwar.checkers.models.database;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.klonwar.checkers.mocks.MockUsers;
import ru.klonwar.checkers.models.game.GameMechanics;
import ru.klonwar.checkers.models.game.Player;
import ru.klonwar.checkers.models.game.PlayerColor;
import ru.klonwar.checkers.models.p2p.ConnectionState;
import ru.klonwar.checkers.models.p2p.GameServer;

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
        QueryResponse qr = testDb.addGame(new GameMechanics(
                new Player(MockUsers.USER_1, PlayerColor.WHITE),
                new Player(MockUsers.USER_2, PlayerColor.BLACK)
        ));
        System.out.println(qr.getMessage());
        Assert.assertTrue(qr.isSuccessful());
    }

    @Test
    public void gettingUserWorksCorrectly() {
        User user = testDb.getUserByLoginAndPassword(MockUsers.USER_2.getLogin(), MockUsers.USER_2.getPassword());
        Assert.assertNotNull(user);
    }

    @Test
    public void gettingTopUsersWorksCorrectly() {
        List<GamerInfo> users = testDb.getTopUsers();
        Assert.assertNotNull(users);
        Assert.assertNotEquals(users.size(), 0);
    }

}
