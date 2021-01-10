package ru.klonwar.checkers.models.game.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.klonwar.checkers.models.database.CheckersDatabase;
import ru.klonwar.checkers.models.game.Field;
import ru.klonwar.checkers.models.game.PlayerColor;
import ru.klonwar.checkers.models.p2p.ConnectionState;
import ru.klonwar.checkers.models.p2p.GameWithDatabase;

import java.util.concurrent.CompletableFuture;

public class SocketServer extends GameWithDatabase implements SocketGame {
    protected ConnectionState cs;

    public SocketServer(ConnectionState cs, CheckersDatabase db) {
        super(db);
        this.cs = cs;
    }

    @Override
    public void connect() {
        if (gm.getCurrentPlayerColor() == PlayerColor.WHITE) {
            enable();
        } else {
            disable();
            readFieldState();
        }
    }

    @Override
    public void nextTurn() {
        super.nextTurn();
        if (!gm.haveWinner()) {
            sendFieldState();
            readFieldState();
        } else {
            ifWinnerFound();
        }
    }

    @Override
    protected void ifWinnerFound() {
        if (gm.haveWinner()) {
            if (gm.getWinner() == gm.getCurrentPlayerColor()) {
                sendFieldState();
                super.ifWinnerFound();
            } else {
                gm.changeShownIndex();
            }
        }
    }

    private void sendFieldState() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            cs.getSc().send(mapper.writeValueAsString(gm.getField()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    private void readFieldState() {
        disable();
        CompletableFuture.supplyAsync(() -> cs.getSc().read())
                .thenAccept((res) -> {
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        Field newState = mapper.readValue(res, Field.class);

                        gm.getField().copyFrom(newState);
                        gm.getActivePlayer().suggestPossibleMoves();
                        gm.checkWinner();

                        if (gm.haveWinner()) {
                            ifWinnerFound();
                        }

                        enable();

                    } catch (Exception err) {
                        throw new RuntimeException(err);
                    }
                });
    }
}
