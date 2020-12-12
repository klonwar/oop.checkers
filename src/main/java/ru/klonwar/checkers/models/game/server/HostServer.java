package ru.klonwar.checkers.models.game.server;

import ru.klonwar.checkers.models.database.CheckersDatabase;
import ru.klonwar.checkers.models.game.GameMechanics;
import ru.klonwar.checkers.models.game.Player;
import ru.klonwar.checkers.models.game.PlayerColor;
import ru.klonwar.checkers.models.p2p.ConnectionState;
import ru.klonwar.checkers.models.p2p.GameServer;

import java.util.Map;

public class HostServer extends SocketServer {

    public HostServer(ConnectionState cs, CheckersDatabase db) {
        super(cs, db);

        // Распределяем цвета
        Map<PlayerColor, Player> colorizedPlayers = GameMechanics.provideColors(
                cs.getThisUser(),
                cs.getOpponentUser()
        );

        this.gm = new GameMechanics(
                colorizedPlayers.get(PlayerColor.WHITE),
                colorizedPlayers.get(PlayerColor.BLACK)
        );

        boolean thisWhite = colorizedPlayers.get(PlayerColor.WHITE).getUser() == cs.getThisUser();
        gm.setCurrentPlayerColor((thisWhite) ? PlayerColor.WHITE : PlayerColor.BLACK);
    }

    @Override
    public void connect() {
        PlayerColor guestColor = gm.getCurrentPlayerColor().nextColor();

        // Слушаем, когда гость подключится
        cs.getSc().read();

        // Сообщим гостю его цвет
        cs.getSc().send(guestColor.ordinal() + "");
        super.connect();
    }
}
