package ru.klonwar.checkers.graphics;

import ru.klonwar.checkers.config.ColorEnum;
import ru.klonwar.checkers.config.Config;
import ru.klonwar.checkers.helpers.Position;
import ru.klonwar.checkers.helpers.geometry.Point;
import ru.klonwar.checkers.helpers.geometry.Vector;
import ru.klonwar.checkers.models.Cell;
import ru.klonwar.checkers.models.Field;

import java.awt.*;
import java.util.ArrayList;

public class FieldGraphics {
    private final CellGraphics[][] cellControllers = new CellGraphics[8][8];

    private int width = 1;
    private final Point[] corners = new Point[4];

    public FieldGraphics(Field field) {
        Cell[][] fieldState = field.getFieldState();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                cellControllers[i][j] = new CellGraphics(fieldState[i][j], ((i + j) % 2 == 0) ? ColorEnum.CELL_BRIGHT.getColor() : ColorEnum.CELL_DARK.getColor());
            }
        }
    }

    private void setWidth(int w) {
        width = w;
        int padding = -(getCellWidth() * 8 - width) / 2;
        corners[0] = new Point(padding, padding);
        corners[1] = new Point(w - padding, padding);
        corners[2] = new Point(w - padding, w - padding);
        corners[3] = new Point(padding, w - padding);
    }

    private void clearField(Graphics2D g2d) {
        g2d.setColor(ColorEnum.BACKGROUND.getColor());
        //noinspection SuspiciousNameCombination
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

    private void drawGrid(Graphics2D g2d, Cell activeCell, ArrayList<Cell> availableToGoTo, ArrayList<Cell> availableToClick) {
        g2d.setStroke(new BasicStroke(Config.LINE_STROKE));
        g2d.setColor(ColorEnum.CELL_BRIGHT.getColor());

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boolean active = cellControllers[i][j].getCell() == activeCell;
                boolean availableTGT = availableToGoTo.contains(cellControllers[i][j].getCell());
                boolean availableTC = availableToClick.contains(cellControllers[i][j].getCell());

                cellControllers[i][j].paint(g2d, getCellCenter(i, j), getCellWidth(), active, availableTGT, availableTC);
            }
        }
    }

    public void paint(Graphics2D g2d, int width, Cell activeCell, ArrayList<Cell> availableToGoTo, ArrayList<Cell> availableToClick) {
        setWidth(width);

        clearField(g2d);
        drawGrid(g2d, activeCell, availableToGoTo, availableToClick);
    }

    public Position getClickedPosition(Point point) {
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
}
