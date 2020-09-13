package ru.klonwar.checkers.graphics;

import ru.klonwar.checkers.config.Config;
import ru.klonwar.checkers.helpers.Position;
import ru.klonwar.checkers.helpers.geometry.Point;
import ru.klonwar.checkers.helpers.geometry.Vector;
import ru.klonwar.checkers.models.Cell;
import ru.klonwar.checkers.models.Checker;
import ru.klonwar.checkers.models.Field;
import ru.klonwar.checkers.models.King;

import java.awt.*;
import java.util.ArrayList;

public class FieldController {
    private final Field field;
    private final CellController[][] cellControllers = new CellController[8][8];

    private int width = 1;
    private final Point[] corners = new Point[4];

    public FieldController() {
        this.field = new Field();

        Cell[][] fieldState = field.getFieldState();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                cellControllers[i][j] = new CellController(fieldState[i][j], ((i + j) % 2 == 0) ? Config.CELL_BRIGHT : Config.CELL_DARK);
            }
        }
    }

    public void clearSuggestion() {
        this.field.clearPossibleCells();
        this.field.setActiveCell(null);
    }

    private void setWidth(int w) {
        this.width = w;
        int padding = -(getCellWidth() * 8 - this.width) / 2;
        corners[0] = new Point(padding, padding);
        corners[1] = new Point(w - padding, padding);
        corners[2] = new Point(w - padding, w - padding);
        corners[3] = new Point(padding, w - padding);
    }

    private void clearField(Graphics2D g2d) {
        g2d.setColor(Config.BACKGROUND);
        g2d.fillRect(0, 0, width, width);
    }

    private int getCellWidth() {
        return width / 8;
    }

    private Point getCellCenter(int x, int y) {
        int cellHeight = getCellWidth();
        Point checkerCenter = corners[0].clone();
        checkerCenter.addVector(new Vector(cellHeight / 2, cellHeight / 2));
        checkerCenter.addVector(new Vector(x * (cellHeight), y * (cellHeight)));

        return checkerCenter;
    }

    private void drawGrid(Graphics2D g2d) {
        g2d.setStroke(new BasicStroke(Config.LINE_STROKE));
        g2d.setColor(Config.CELL_BRIGHT);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                cellControllers[i][j].getCell().setActive(cellControllers[i][j].getCell() == this.field.getActiveCell());
                cellControllers[i][j].getCell().setPossible(this.field.getPossibleCells().contains(cellControllers[i][j].getCell()));
                cellControllers[i][j].paint(getCellCenter(i, j), getCellWidth(), g2d);
            }
        }
    }

    public void paint(int width, Graphics2D g2d) {
        setWidth(width);

        clearField(g2d);
        drawGrid(g2d);
    }

    private Position getPosition(Cell cell) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (cell == this.cellControllers[i][j].getCell()) {
                    return new Position(i, j);
                }
            }
        }
        return null;
    }

    private Cell getCellFromPosition(Position position) {
        if (!isInField(position)) return null;
        return this.cellControllers[position.getFirst()][position.getSecond()].getCell();
    }

    private Position getClickedPosition(Point point) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Cell clicked = cellControllers[i][j].onClick(point);
                if (clicked != null) {
                    return new Position(i, j);
                }
            }
        }
        return null;
    }

    private void moveChecker(Position from, Position to) {
        int deltaH = Math.round(Math.signum(to.getFirst() - from.getFirst()));
        int deltaV = Math.round(Math.signum(to.getSecond() - from.getSecond()));
        Cell toCell;
        Cell fromCell = getCellFromPosition(from);

        if (deltaH != 0 || deltaV != 0) {
            for (
                    int i = from.getFirst() + deltaH, j = from.getSecond() + deltaV;
                    ((deltaH >= 0) ? i <= to.getFirst() : i >= to.getFirst()) || ((deltaV >= 0) ? j <= to.getSecond() : j >= to.getSecond());
                    i += deltaH, j += deltaV
            ) {
                toCell = getCellFromPosition(new Position(i, j));

                if (toCell == null || fromCell == null) return;

                toCell.setChecker(fromCell.getChecker());
                fromCell.setChecker(null);

                fromCell = toCell;
            }
        }
    }

    public void onClick(Point point) {
        Position activePosition = getPosition(this.field.getActiveCell());
        Position clickedPosition = getClickedPosition(point);

        if (clickedPosition == null) return;

        Cell clicked = getCellFromPosition(clickedPosition);
        if (activePosition != null && clicked != null && clicked.isPossible()) {
            moveChecker(activePosition, clickedPosition);
        }

        if (clicked == this.field.getActiveCell()) {
            this.field.setActiveCell(null);
        } else {
            this.field.setActiveCell(clicked);
        }

        this.field.clearPossibleCells();
        if (this.field.getActiveCell() != null) {
            this.field.setPossibleCells(findPossibleCellsFor(this.field.getActiveCell()));
        }
    }

    private ArrayList<Cell> findPossibleCellsFor(Cell target) {
        Boolean[][] checkableField = new Boolean[8][8];
        Position position = getPosition(target);

        /*
         * Т.к. при возможности бить шашку необходимо это сделать, то
         * введем два массива: possible и required, которые помогут
         * отделить котлеты от мух
         * */

        ArrayList<Cell> possible = new ArrayList<>();
        ArrayList<Cell> required = new ArrayList<>();
        fpcRecursion(checkableField, possible, required, position, position);

        return (required.size() > 0) ? required : possible;
    }

    private boolean isInField(Position position) {
        return !(position.getFirst() < 0 ||
                position.getFirst() >= 8 ||
                position.getSecond() < 0 ||
                position.getSecond() >= 8
        );
    }

    private void fpcRecursion(Boolean[][] checkableField, ArrayList<Cell> possible, ArrayList<Cell> required, Position now, Position target) {
        if (!isInField(now) || checkableField[now.getFirst()][now.getSecond()] != null) {
            return;
        }

        checkableField[now.getFirst()][now.getSecond()] = true;

        Cell nowCell = this.cellControllers[now.getFirst()][now.getSecond()].getCell();
        Cell targetCell = this.cellControllers[target.getFirst()][target.getSecond()].getCell();

        if (targetCell.getChecker() == null) {
            return;
        }

        int nowH = now.getFirst();
        int nowV = now.getSecond();
        int targetH = target.getFirst();
        int targetV = target.getSecond();

        if (nowCell.getChecker() == null) {
            Checker targetChecker = targetCell.getChecker();
            if (targetChecker instanceof King) {
                // todo
            } else {
                if (targetChecker.getColor() == 0) {
                    if (nowV - targetV == 1 && Math.abs(nowH - targetH) == 1) {
                        possible.add(nowCell);
                    }
                } else {
                    if (targetV - nowV == 1 && Math.abs(nowH - targetH) == 1) {
                        possible.add(nowCell);
                    }
                }

                int deltaV = targetV - nowV;
                int deltaH = targetH - nowH;

                if (Math.abs(deltaV) == 2 && Math.abs(deltaH) == 2) {
                    int evilH = targetH - deltaH / 2;
                    int evilV = targetV - deltaV / 2;
                    if (isInField(new Position(evilH, evilV))) {
                        Checker evilChecker = this.cellControllers[evilH][evilV].getCell().getChecker();

                        if (evilChecker != null && evilChecker.getColor() != targetChecker.getColor()) {
                            required.add(nowCell);
                        }
                    }
                }
            }
        }

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                fpcRecursion(checkableField, possible, required, new Position(now.getFirst() + i, now.getSecond() + j), target);
            }
        }

    }
}