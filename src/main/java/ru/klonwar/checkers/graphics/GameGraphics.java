package ru.klonwar.checkers.graphics;

import ru.klonwar.checkers.config.ColorEnum;
import ru.klonwar.checkers.config.Config;
import ru.klonwar.checkers.util.Pair;
import ru.klonwar.checkers.util.Position;
import ru.klonwar.checkers.util.geometry.Point;
import ru.klonwar.checkers.models.game.Cell;
import ru.klonwar.checkers.models.game.Game;
import ru.klonwar.checkers.models.game.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameGraphics {
    private Game game = null;
    private FieldGraphics fieldGraphics = null;
    private final MoveGraphics moveGraphics = new MoveGraphics();
    private final WaitingGraphics waitingGraphics = new WaitingGraphics();
    private final Runnable repaint;

    public GameGraphics(Runnable repaint) {
        this.repaint = repaint;
    }

    public void setGame(Game game) {
        this.game = game;
        fieldGraphics = new FieldGraphics(game.getField());
    }

    public Game getGame() {
        return game;
    }

    public void restart() {
        // todo
        // game = new Game();
        // fieldGraphics = new FieldGraphics(game.getField());
    }

    public void onClick(Point point) {
        if (game != null) {
            Player player = game.getActivePlayer();

            Cell activeCell = player.getActiveCell();
            Position activePosition = game.getField().getPositionFromCell(activeCell);

            Position clickedPosition = fieldGraphics.getClickedPosition(point);
            Cell clickedCell = game.getField().getCellFromPosition(clickedPosition);

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

                    game.checkWinner();
                    // Меняем игрока
                    if (!game.haveWinner())
                        game.switchPlayer();
                    else
                        game.enable();

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
        game.getActivePlayer().clearActiveCell();
    }

    public void paint(Graphics2D g2d, int w, int h) {
        int size = Math.min(w, h);
        if (game != null) {
            Player player = game.getActivePlayer();
            Position activePosition = game.getField().getPositionFromCell(player.getActiveCell());

            ArrayList<Cell> availableToGoTo = new ArrayList<>();
            if (activePosition != null) {
                for (Pair<Position, Position> item : player.getAvailableMoves()) {
                    if (item.getFirst().equals(activePosition)) {
                        availableToGoTo.add(game.getField().getCellFromPosition(item.getSecond()));
                    }
                }
            }

            if (!game.haveWinner()) {
                fieldGraphics.paint(g2d, size, player.getActiveCell(), availableToGoTo, player.getAvailableToClickCells());
            }
            moveGraphics.paint(g2d, player, new Point(size, 0), game.haveWinner());
        } else {
            g2d.setColor(ColorEnum.BLACK.getColor());
            g2d.setFont(Config.FONT);
            g2d.drawString("Игра не начата", 0, Config.FONT_SIZE);
        }

        if (!game.isEnabled())
            waitingGraphics.paint(g2d, w, h);
    }

}
