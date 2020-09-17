package ru.klonwar.checkers.models;

public class Game {
    private final int playersCount = 2;

    private final Field field;
    private int activePlayerIndex = 1;
    private final Player[] players = new Player[playersCount];
    private boolean gameOver;

    {
        field = new Field();

        for (int i = 0; i < playersCount; i++) {
            players[i] = new Player(field, i);
        }
    }

    public Player getActivePlayer() {
        return players[activePlayerIndex];
    }

    public void switchPlayer() {
        activePlayerIndex++;
        if (activePlayerIndex >= players.length) activePlayerIndex = 0;

        getActivePlayer().suggestPossibleMoves();
    }

    public Field getField() {
        return field;
    }
}
