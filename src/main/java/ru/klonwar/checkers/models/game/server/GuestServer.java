package ru.klonwar.checkers.models.game.server;

import ru.klonwar.checkers.models.database.CheckersDatabase;
import ru.klonwar.checkers.models.game.GameMechanics;
import ru.klonwar.checkers.models.game.Player;
import ru.klonwar.checkers.models.game.PlayerColor;
import ru.klonwar.checkers.models.p2p.ConnectionState;

public class GuestServer extends SocketServer {

    public GuestServer(ConnectionState cs, CheckersDatabase db) {
        super(cs, db);
    }

    @Override
    public void connect() {
        // Отправили, что подключены
        if (cs.getSc() != null)
            cs.getSc().send("ok");

        // Получили свой цвет
        PlayerColor thisColor = PlayerColor.values()[Integer.parseInt((cs.getSc() != null) ? cs.getSc().read() : "0")];

        // Созадим у себя плееров
        if (thisColor == PlayerColor.WHITE) {
            // Мы сейчас будем ходить
            this.gm = new GameMechanics(
                    new Player(cs.getThisUser(), PlayerColor.WHITE),
                    new Player(cs.getOpponentUser(), PlayerColor.BLACK)
            );
        } else {
            // Хост сейчас будет ходить
            this.gm = new GameMechanics(
                    new Player(cs.getOpponentUser(), PlayerColor.WHITE),
                    new Player(cs.getThisUser(), PlayerColor.BLACK)
            );
        }
        gm.setCurrentPlayerColor(thisColor);
        super.connect();
    }
}
