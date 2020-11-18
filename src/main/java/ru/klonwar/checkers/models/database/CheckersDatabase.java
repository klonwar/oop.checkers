package ru.klonwar.checkers.models.database;

import ru.klonwar.checkers.models.game.Game;

import java.util.List;

public interface CheckersDatabase {
    QueryResponse addUser(User user);

    QueryResponse addGame(Game game);

    List<GameInfo> getGamesInfoForUserID(int userID);

    User getUserByLoginAndPassword(String login, String password);
}
