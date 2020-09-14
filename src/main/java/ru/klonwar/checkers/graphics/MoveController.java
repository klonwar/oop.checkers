package ru.klonwar.checkers.graphics;

import ru.klonwar.checkers.config.Config;
import ru.klonwar.checkers.helpers.Position;
import ru.klonwar.checkers.helpers.geometry.Point;
import ru.klonwar.checkers.models.Cell;
import ru.klonwar.checkers.models.Checker;

import java.awt.*;
import java.util.ArrayList;

public class MoveController {
    private ArrayList<Cell> requiredCells = new ArrayList<>();

    public Integer getActivePlayerColor() {
        return activePlayerColor;
    }

    public void setActivePlayerColor(int activePlayerColor) {
        this.activePlayerColor = activePlayerColor;
    }

    public MoveController() {

    }

    private Integer activePlayerColor = 1;

    public void paint(Point point, Graphics2D g2d) {
        g2d.setStroke(new BasicStroke(Config.LINE_STROKE));
        g2d.setColor(Config.BLACK);
        g2d.setFont(Config.FONT);
        g2d.drawString("Ходит:", point.getX(), point.getY() + Config.FONT_SIZE);

        CheckerController activePlayerChecker = new CheckerController(new Cell(new Checker(activePlayerColor)));
        int r = Config.FONT_SIZE;
        activePlayerChecker.paint(new Point(point.getX() + r, point.getY() + 5 * r / 2), r, g2d);
    }

    public void changePlayer() {
        this.activePlayerColor += 1;
        this.activePlayerColor %= 2;
    }

    public ArrayList<Cell> getRequiredCells() {
        return requiredCells;
    }

    public void setRequiredCells(ArrayList<Cell> requiredCells) {
        this.requiredCells = requiredCells;
    }
    public void addToRequiredCells(Cell cell) {
        this.requiredCells.add(cell);
    }
    public void clearRequiredCells() {
        this.requiredCells = new ArrayList<>();
    }
}
