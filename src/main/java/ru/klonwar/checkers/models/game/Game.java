package ru.klonwar.checkers.models.game;

import ru.klonwar.checkers.models.database.CheckersDatabase;
import ru.klonwar.checkers.models.database.QueryResponse;
import ru.klonwar.checkers.models.database.User;
import ru.klonwar.checkers.models.game.Field;
import ru.klonwar.checkers.models.game.Player;

import javax.swing.*;

public class Game {
    private final Field field;
    private int activePlayerIndex = 0;
    private final Player whitePlayer;
    private final Player blackPlayer;

    private Integer winner = null;
    private Long finishTime = null;
    private CheckersDatabase db;

    public Game(User user1, User user2, CheckersDatabase database) {
        field = new Field();
        User[] users = new User[2];
        users[0] = user1;
        users[1] = user2;

        int absolutelyRandomIndex = (int) Math.abs((System.currentTimeMillis()) % 2);
        int anotherIndex = (absolutelyRandomIndex == 0) ? 1 : 0;

        whitePlayer = new Player(users[absolutelyRandomIndex], field, 1);
        blackPlayer = new Player(users[anotherIndex], field, 0);
        this.db = database;
    }

    public Player getActivePlayer() {
        return (activePlayerIndex == 0) ? whitePlayer : blackPlayer;
    }

    public int getActivePlayerIndex() {
        return activePlayerIndex;
    }

    private void changePlayerIndex() {
        activePlayerIndex = (activePlayerIndex == 1) ? 0 : 1;
    }

    /**
     * Механика смены активного игрока на следующего
     */

    public void switchPlayer() {
        changePlayerIndex();
        checkWinner();
    }

    /**
     * Проверка активного игрока на наличие ходов
     */

    public void checkWinner() {
        getActivePlayer().suggestPossibleMoves();
        if (getActivePlayer().getAvailableToClickCells().size() == 0) {
            // Если у текущего игрока нет ходов, то победил предыдущий
            changePlayerIndex();
            finish(activePlayerIndex);
        }
    }

    public Field getField() {
        return field;
    }

    public boolean haveWinner() {
        return winner != null;
    }

    public Integer getWinner() {
        return winner;
    }

    public QueryResponse finish(Integer winner) {
        this.winner = winner;
        this.finishTime = System.currentTimeMillis();

        return db.addGame(this);
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public Player getBlackPlayer() {
        return blackPlayer;
    }

    public Long getFinishTime() {
        return finishTime;
    }
}
