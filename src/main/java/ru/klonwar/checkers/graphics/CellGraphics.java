package ru.klonwar.checkers.graphics;

import ru.klonwar.checkers.config.ColorEnum;
import ru.klonwar.checkers.config.Config;
import ru.klonwar.checkers.helpers.geometry.Point;
import ru.klonwar.checkers.helpers.geometry.Vector;
import ru.klonwar.checkers.models.game.Cell;

import java.awt.*;

public class CellGraphics {
    private Point leftTop = new Point(0, 0);
    private Point rightBot = new Point(0, 0);
    private final Color cellColor;
    private Cell cell;

    public CellGraphics(Cell cell, Color cellColor) {
        this.cell = cell;
        this.cellColor = cellColor;

    }

    private boolean isPointInsideCell(Point point) {
        return (point.getX() >= leftTop.getX() && point.getX() <= rightBot.getX() && point.getY() >= leftTop.getY() && point.getY() <= rightBot.getY());
    }

    public void paint(Graphics2D g2d, Point center, int width, boolean active, boolean availableToGoTo, boolean availableToClick) {
        leftTop = center.clone().addVector(new Vector(-1 * (width) / 2, -1 * (width) / 2));
        rightBot = center.clone().addVector(new Vector(width / 2, width / 2));

        g2d.setColor(cellColor);
        MyGraphics.fillSquareWithCenter(g2d, center, width);

        if (availableToGoTo) {
            g2d.setColor(ColorEnum.POSSIBLE_COLOR.getColor());
            MyGraphics.drawSquareWithCenter(g2d, center, width - Config.LINE_STROKE);
        }

        if (availableToClick) {
            g2d.setColor(ColorEnum.REQUIRED_COLOR.getColor());
            MyGraphics.drawSquareWithCenter(g2d, center, width - Config.LINE_STROKE);
        }

        if (active) {
            g2d.setColor(ColorEnum.ACTIVE_COLOR.getColor());
            MyGraphics.drawSquareWithCenter(g2d, center, width - Config.LINE_STROKE);
        }

        if (cell.getChecker() != null) {
            g2d.setColor((cell.getChecker().getColor() == 0) ? ColorEnum.BLACK_CHECKER.getColor() : ColorEnum.WHITE_CHECKER.getColor());
            MyGraphics.fillCircleWithCenter(g2d, center, 2 * width / 5);

            if (cell.getChecker().isKing()) {
                g2d.setColor(ColorEnum.KING_ACCENT.getColor());
                MyGraphics.fillArcWithCenter(g2d, center, 2 * width / 5, 45, -4*45);
            }
        }
    }

    public Cell onClick(Point point) {
        if (isPointInsideCell(point)) return cell;
        return null;
    }

    public Cell getCell() {
        return this.cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

}
