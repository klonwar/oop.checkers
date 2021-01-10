package ru.klonwar.checkers.graphics;

import ru.klonwar.checkers.config.ColorEnum;
import ru.klonwar.checkers.config.Config;
import ru.klonwar.checkers.models.game.server.SocketServer;
import ru.klonwar.checkers.models.p2p.GameWithDatabase;
import ru.klonwar.checkers.util.Pair;
import ru.klonwar.checkers.util.Position;
import ru.klonwar.checkers.util.geometry.Point;
import ru.klonwar.checkers.models.game.Cell;
import ru.klonwar.checkers.models.game.GameMechanics;
import ru.klonwar.checkers.models.game.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameGraphics {
    private SocketServer gameServer = null;
    private FieldGraphics fieldGraphics = null;
    private final MoveGraphics moveGraphics = new MoveGraphics();
    private final WaitingGraphics waitingGraphics = new WaitingGraphics();
    private final Runnable repaint;

    public GameGraphics(Runnable repaint) {
        this.repaint = repaint;
    }

    public GameWithDatabase getGameServer() {
        return gameServer;
    }

    public void setGameServer(SocketServer gameServer) {
        this.gameServer = gameServer;
        fieldGraphics = new FieldGraphics(gameServer.getField());
    }

    public GameMechanics getGameMechanics() {
        return gameServer.getMechanics();
    }

    public void onClick(Point point) {
        if (gameServer != null) {
            Player player = gameServer.getActivePlayer();

            Cell activeCell = player.getActiveCell();
            Position activePosition = gameServer.getField().getPositionFromCell(activeCell);

            Position clickedPosition = fieldGraphics.getClickedPosition(point);
            Cell clickedCell = gameServer.getField().getCellFromPosition(clickedPosition);

            if (clickedPosition == null || clickedCell == null) return;

            /*
             * Передвижение шашки
             * Если после хода больше вариантов нет,
             * то ход переходит к другому игроку
             * */

            if (activePosition != null && player.getAvailableMoves().contains(new Pair<>(activePosition, clickedPosition))) {
                boolean endOfTurn = player.moveChecker(activePosition, clickedPosition);
                if (endOfTurn) {
                    SwingUtilities.invokeLater(repaint);

                    // Меняем игрока
                    if (!gameServer.haveWinner())
                        gameServer.nextTurn();
                    else
                        gameServer.enable();

                    player.clearActiveCell();
                } else {
                    ArrayList<Cell> temp = new ArrayList<>();
                    temp.add(player.getActiveCell());
                    player.setAvailableToClickCells(temp);
                }
                return;
            }

            /*
             * При клике на ячейку она станет активной
             * */

            if (clickedCell == player.getActiveCell()) {
                player.setActiveCell(null);
            } else if (clickedCell.getChecker() != null && clickedCell.getChecker().getColor() == player.getColor() && player.getAvailableToClickCells().contains(clickedCell)) {
                player.setActiveCell(clickedCell);
            }
        }
    }

    public void clearHints() {
        gameServer.getActivePlayer().clearActiveCell();
    }

    public void paint(Graphics2D g2d, int w, int h) {
        int size = Math.min(w, h);
        if (gameServer != null) {
            Player player = gameServer.getActivePlayer();
            Position activePosition = gameServer.getField().getPositionFromCell(player.getActiveCell());

            ArrayList<Cell> availableToGoTo = new ArrayList<>();
            if (activePosition != null) {
                for (Pair<Position, Position> item : player.getAvailableMoves()) {
                    if (item.getFirst().equals(activePosition)) {
                        availableToGoTo.add(gameServer.getField().getCellFromPosition(item.getSecond()));
                    }
                }
            }

            if (!gameServer.haveWinner()) {
                fieldGraphics.paint(g2d, size, player.getActiveCell(), availableToGoTo, player.getAvailableToClickCells());
            }
            moveGraphics.paint(g2d, player, new Point(size, 0), gameServer.haveWinner());
        } else {
            g2d.setColor(ColorEnum.BLACK.getColor());
            g2d.setFont(Config.FONT);
            g2d.drawString("Игра не начата", 0, Config.FONT_SIZE);
        }

        if (!gameServer.isEnabled())
            waitingGraphics.paint(g2d, w, h);
    }

}
