package ru.klonwar.checkers.models;

public class Game {
    private final int playersCount = 2;

    private final Field field;
    private int activePlayerIndex = 1;
    private final Player[] players = new Player[playersCount];
    private Integer winner = null;

    public Game() {
        field = new Field();

        for (int i = 0; i < playersCount; i++) {
            players[i] = new Player(field, i);
        }
    }

    public Player getActivePlayer() {
        return players[activePlayerIndex];
    }

    private void changePlayerIndex() {
        activePlayerIndex = (activePlayerIndex == 1) ? 0 : 1;
    }

    /**
     * Механика смены активного игрока на следующего
     * */

    public void switchPlayer() {
        changePlayerIndex();
        checkWinner();
    }

    /**
     * Проверка активного игрока на наличие ходов
     * */

    public void checkWinner() {
        getActivePlayer().suggestPossibleMoves();
        if (getActivePlayer().getAvailableToClickCells().size() == 0) {
            // Если у текущего игрока нет ходов, то победил предыдущий
            changePlayerIndex();
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
