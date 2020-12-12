package ru.klonwar.checkers.models.game;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.klonwar.checkers.mocks.MockUsers;
import ru.klonwar.checkers.models.database.User;

import java.util.Map;

public class GameMechanicsTest {
    private GameMechanics gameMechanics;

    @Before
    public void initObjects() {
        gameMechanics = new GameMechanics(
                new Player(MockUsers.USER_1, PlayerColor.WHITE),
                new Player(MockUsers.USER_2, PlayerColor.BLACK)
        );
    }

    @Test
    public void gameProvidesColorsCorrectly() {
        Map<PlayerColor, Player> coloredUsers = GameMechanics.provideColors(MockUsers.USER_1, MockUsers.USER_2);
        User whiteUser = coloredUsers.get(PlayerColor.WHITE).getUser();
        User blackUser = coloredUsers.get(PlayerColor.BLACK).getUser();
        Assert.assertTrue(whiteUser == MockUsers.USER_1 || whiteUser == MockUsers.USER_2);
        Assert.assertTrue(blackUser == MockUsers.USER_1 || blackUser == MockUsers.USER_2);
    }

    @Test
    public void winnerDetectingCorrectly() {
        PlayerColor winnerAtStart = gameMechanics.checkWinner();
        Assert.assertNull(winnerAtStart);

        Field field = gameMechanics.getField();
        for (Cell[] row : field.getFieldState()) {
            for (Cell item : row) {
                if (item.getChecker() != null && item.getChecker().getColor() == PlayerColor.BLACK) {
                    item.setChecker(null);
                }
            }
        }

        PlayerColor winnerAtEnd = gameMechanics.checkWinner();
        Assert.assertEquals(winnerAtEnd, PlayerColor.WHITE);
    }
}
