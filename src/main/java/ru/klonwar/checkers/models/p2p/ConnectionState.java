package ru.klonwar.checkers.models.p2p;

import ru.klonwar.checkers.models.database.CheckersDatabase;
import ru.klonwar.checkers.models.database.User;

public class ConnectionState {
    private User thisUser;
    private User opponentUser;
    private SocketCommunicator sc;
    private ClientType thisType;
    private boolean isActive = false;
    private CheckersDatabase db;

    public ConnectionState() {
    }

    public ConnectionState(User thisUser, User opponentUser) {
        this.setThisUser(thisUser);
        this.setOpponentUser(opponentUser);
    }

    public CheckersDatabase getDb() {
        return db;
    }

    public void setDb(CheckersDatabase db) {
        this.db = db;
    }

    public User getThisUser() {
        return thisUser;
    }

    public void setThisUser(User thisUser) {
        this.thisUser = thisUser;
    }

    public User getOpponentUser() {
        return opponentUser;
    }

    public void setOpponentUser(User opponentUser) {
        this.opponentUser = opponentUser;
    }

    public SocketCommunicator getSc() {
        return sc;
    }

    public void setSc(SocketCommunicator sc) {
        this.sc = sc;
    }

    public ClientType getThisType() {
        return thisType;
    }

    public void setThisType(ClientType thisType) {
        this.thisType = thisType;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }
}
