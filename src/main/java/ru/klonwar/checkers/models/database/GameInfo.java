package ru.klonwar.checkers.models.database;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class GameInfo {
    @JsonProperty("ID")
    private int id;
    @JsonProperty("WHITE_USER_ID")
    private int whiteUserId;
    @JsonProperty("BLACK_USER_ID")
    private int blackUserId;
    @JsonProperty("WINNER")
    private int activePlayer;
    @JsonProperty("FINISH_TIME")
    private long finishTime;

    public GameInfo() {
    }

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
