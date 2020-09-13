package ru.klonwar.checkers.graphics;

import ru.klonwar.checkers.config.Config;
import ru.klonwar.checkers.helpers.geometry.Point;
import ru.klonwar.checkers.helpers.geometry.Vector;
import ru.klonwar.checkers.models.Cell;

import java.awt.*;

public class CellController {
    private final CheckerController checkerController;
    private Point leftTop = new Point(0, 0);
    private Point rightBot = new Point(0, 0);
    private final Color cellColor;
    private Cell cell;

    public CellController(Cell cell, Color cellColor) {
        this.cell = cell;
        this.cellColor = cellColor;

        checkerController = new CheckerController(cell);
    }

    private boolean isPointInsideCell(Point point) {
        return (point.getX() >= leftTop.getX() && point.getX() <= rightBot.getX() && point.getY() >= leftTop.getY() && point.getY() <= rightBot.getY());
    }

    public void paint(Point center, int width, Graphics2D g2d) {
        leftTop = center.clone().addVector(new Vector(-1 * (width) / 2, -1 * (width) / 2));
        rightBot = center.clone().addVector(new Vector(width / 2, width / 2));

        g2d.setColor(cellColor);
        MyGraphics.fillSquareWithCenter(g2d, center, width);

        if (cell.isPossible()) {
            g2d.setColor(Config.POSSIBLE_COLOR);
            MyGraphics.drawSquareWithCenter(g2d, center, width - Config.LINE_STROKE);
        }

        if (cell.isActive()) {
            g2d.setColor(Config.ACTIVE_COLOR);
            MyGraphics.drawSquareWithCenter(g2d, center, width - Config.LINE_STROKE);
        }
        // ... cell.getChecker();
        this.checkerController.paint(center, 2 * width / 5, g2d);
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

    public CheckerController getCheckerController() {
        return this.checkerController;
    }

}
