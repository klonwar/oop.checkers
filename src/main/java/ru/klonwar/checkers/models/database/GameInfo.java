package ru.klonwar.checkers.models.database;

import java.util.Date;

public class GameInfo {
    private final int id;
    private int whiteUserId;
    private int blackUserId;
    private int activePlayer;
    private long finishTime;

    public GameInfo(int id, int whiteUserId, int blackUserId, int activePlayer, long finishTime) {
        this.id = id;
        this.whiteUserId = whiteUserId;
        this.blackUserId = blackUserId;
        this.activePlayer = activePlayer;
        this.finishTime = finishTime;
    }

    public int getId() {
        return id;
    }

    public int getWhiteUserId() {
        return whiteUserId;
    }

    public int getBlackUserId() {
        return blackUserId;
    }

    public int getActivePlayer() {
        return activePlayer;
    }

    public long getFinishTime() {
        return finishTime;
    }

    @Override
    public String toString() {
        return "\nGameInfo {" +
                "\n\tid=" + id +
                ",\n\twhiteUserId=" + whiteUserId +
                ",\n\tblackUserId=" + blackUserId +
                ",\n\tactivePlayer=" + activePlayer +
                ",\n\tfinishTime=" + finishTime +
                "\n}";
    }
}
