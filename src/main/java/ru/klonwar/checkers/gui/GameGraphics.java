package ru.klonwar.checkers.gui;

import ru.klonwar.checkers.graphics.FieldGraphics;
import ru.klonwar.checkers.graphics.MoveGraphics;
import ru.klonwar.checkers.helpers.Pair;
import ru.klonwar.checkers.helpers.Position;
import ru.klonwar.checkers.helpers.geometry.Point;
import ru.klonwar.checkers.models.game.Cell;
import ru.klonwar.checkers.models.game.Game;
import ru.klonwar.checkers.models.game.Player;

import java.awt.*;
import java.util.ArrayList;

public class GameGraphics {
    private Game game;
    private FieldGraphics fieldGraphics;
    private final MoveGraphics moveGraphics = new MoveGraphics();

    public GameGraphics() {
        this.game = new Game();
        fieldGraphics = new FieldGraphics(game.getField());
    }

    public void restart() {
        // todo
        game = new Game();
        fieldGraphics = new FieldGraphics(game.getField());
    }

    public void onClick(Point point) {
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
                game.switchPlayer();
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

    public void clearHints() {
        game.getActivePlayer().clearActiveCell();
    }

    public void paint(Graphics2D g2d, int size) {
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
        moveGraphics.paint(g2d, player.getColor(), new Point(size, 0), game.haveWinner());
    }
}
