package ru.klonwar.checkers.graphics;

import ru.klonwar.checkers.config.ColorEnum;
import ru.klonwar.checkers.config.Config;
import ru.klonwar.checkers.util.Position;
import ru.klonwar.checkers.util.geometry.Point;
import ru.klonwar.checkers.util.geometry.Vector;
import ru.klonwar.checkers.models.game.Cell;
import ru.klonwar.checkers.models.game.Field;

import java.awt.*;
import java.util.List;

public class FieldGraphics {
    private final CellGraphics[][] cellsGraphics = new CellGraphics[Field.height][Field.width];

    private int width = 1;
    private final Point[] corners = new Point[4];

    public FieldGraphics(Field field) {
        Cell[][] fieldState = field.getFieldState();
        for (int i = 0; i < Field.height; i++) {
            for (int j = 0; j < Field.width; j++) {
                cellsGraphics[i][j] = new CellGraphics(fieldState[i][j], ((i + j) % 2 == 0) ? ColorEnum.CELL_BRIGHT.getColor() : ColorEnum.CELL_DARK.getColor());
            }
        }
    }

    private void setWidth(int w) {
        width = w;
        int padding = -(getCellWidth() * Field.width - width) / 2;
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
        return width / Field.width;
    }

    private Point getCellCenter(int x, int y) {
        int cellHeight = getCellWidth();
        Point checkerCenter = corners[0].clone();
        checkerCenter.addVector(new Vector(cellHeight / 2, cellHeight / 2));
        checkerCenter.addVector(new Vector(x * (cellHeight), y * (cellHeight)));

        return checkerCenter;
    }

    private void drawGrid(Graphics2D g2d, Cell activeCell, List<Cell> availableToGoTo, List<Cell> availableToClick) {
        g2d.setStroke(new BasicStroke(Config.LINE_STROKE));
        g2d.setColor(ColorEnum.CELL_BRIGHT.getColor());

        for (int i = 0; i < Field.height; i++) {
            for (int j = 0; j < Field.width; j++) {
                boolean active = cellsGraphics[i][j].getCell() == activeCell;
                boolean availableTGT = availableToGoTo.contains(cellsGraphics[i][j].getCell());
                boolean availableTC = availableToClick.contains(cellsGraphics[i][j].getCell());

                cellsGraphics[i][j].paint(g2d, getCellCenter(i, j), getCellWidth(), active, availableTGT, availableTC);
            }
        }
    }

    public void paint(Graphics2D g2d, int width, Cell activeCell, List<Cell> availableToGoTo, List<Cell> availableToClick) {
        setWidth(width);

        clearField(g2d);
        drawGrid(g2d, activeCell, availableToGoTo, availableToClick);
    }

    public Position getClickedPosition(Point point) {
        for (int i = 0; i < Field.height; i++) {
            for (int j = 0; j < Field.width; j++) {
                Cell clicked = cellsGraphics[i][j].onClick(point);
                if (clicked != null) {
                    return new Position(i, j);
                }
            }
        }
        return null;
    }
}
