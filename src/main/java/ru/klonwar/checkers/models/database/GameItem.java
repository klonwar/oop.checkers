package ru.klonwar.checkers.models.database;

public class GameItem {
    private final int id;
    private int whiteUserId;
    private int blackUserId;
    private int activePlayer;
    private String fieldState;
    private boolean isActive;

    public GameItem(int id, int whiteUserId, int blackUserId, int activePlayer, String fieldState, boolean isActive) {
        this.id = id;
        this.whiteUserId = whiteUserId;
        this.blackUserId = blackUserId;
        this.activePlayer = activePlayer;
        this.fieldState = fieldState;
        this.isActive = isActive;
    }


}
