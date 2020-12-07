package ru.klonwar.checkers.models.p2p;

import ru.klonwar.checkers.models.database.User;
import ru.klonwar.checkers.models.game.Field;

import java.net.Socket;

public class ConnectionState {
    public User thisUser;
    public User opponentUser;
    public SocketCommunicator sc;
    public Field fieldState;
    public ClientType thisType;
    public boolean activeUser = false;

    public ConnectionState() {
    }

    public ConnectionState(User thisUser, User opponentUser) {
        this.thisUser = thisUser;
        this.opponentUser = opponentUser;
    }
}
