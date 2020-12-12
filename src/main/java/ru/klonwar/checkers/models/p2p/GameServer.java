package ru.klonwar.checkers.models.p2p;

import ru.klonwar.checkers.models.database.CheckersDatabase;
import ru.klonwar.checkers.models.database.User;
import ru.klonwar.checkers.models.game.Field;
import ru.klonwar.checkers.models.game.GameMechanics;
import ru.klonwar.checkers.models.game.Player;
import ru.klonwar.checkers.models.game.PlayerColor;

import java.util.Collections;
import java.util.Map;

public class GameServer {
    protected GameMechanics gm;
    protected boolean isEnabled;
    protected CheckersDatabase db;

    public GameServer(CheckersDatabase db) {
        this.db = db;
    }

    public void nextTurn() {
        gm.nextTurn();
    }

    public void disable() {
        isEnabled = false;
        gm.getActivePlayer().clearActiveCell();
        gm.getActivePlayer().setAvailableToClickCells(Collections.emptyList());
    }

    public void enable() {
        isEnabled = true;
        gm.getActivePlayer().suggestPossibleMoves();
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public GameMechanics getMechanics() {
        return gm;
    }

    public void setMechanics(GameMechanics gm) {
        this.gm = gm;
    }

    public Player getActivePlayer() {
        return gm.getActivePlayer();
    }

    public Field getField() {
        return gm.getField();
    }

    public boolean haveWinner() {
        return gm.haveWinner();
    }

    protected void ifWinnerFound() {
        db.addGame(gm);
    }
}
