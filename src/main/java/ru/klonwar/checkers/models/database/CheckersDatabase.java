package ru.klonwar.checkers.models.database;

import ru.klonwar.checkers.models.game.GameMechanics;

import java.util.List;

public interface CheckersDatabase {
    QueryResponse addUser(User user);

    QueryResponse addGame(GameMechanics gameMechanics);

    List<GamerInfo> getTopUsers();

    User getUserByLoginAndPassword(String login, String password);
}
