package ru.klonwar.checkers.models.p2p;

import ru.klonwar.checkers.models.database.User;
import ru.klonwar.checkers.models.game.Field;

import java.net.Socket;

public class ConnectionState {
    public User thisUser;
    public User opponentUser;
    public SocketCommunicator sc;

    public Field fieldState;
}
