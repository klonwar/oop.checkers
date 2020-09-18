package ru.klonwar.checkers.models;

public class Game {
    private final int playersCount = 2;

    private final Field field;
    private int activePlayerIndex = 1;
    private final Player[] players = new Player[playersCount];
    private Integer winner = null;

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
        if (getActivePlayer().getAvailableToClickCells().size() == 0) {
            --activePlayerIndex;
            if (activePlayerIndex < 0) activePlayerIndex = 1;
            setWinner(activePlayerIndex);
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

    public void setWinner(Integer winner) {
        this.winner = winner;
    }
}
