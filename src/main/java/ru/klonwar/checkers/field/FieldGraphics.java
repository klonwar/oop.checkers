package ru.klonwar.checkers.field;

import ru.klonwar.checkers.config.Config;
import ru.klonwar.checkers.cell.CellGraphics;
import ru.klonwar.checkers.geometry.Point;
import ru.klonwar.checkers.geometry.Vector;

import java.awt.*;

public class FieldGraphics {
    private final CellGraphics[][] cellGraphics;

    private final Graphics2D g2d;
    private int width = 1;
    private final Point[] corners = new Point[4];

    public FieldGraphics(CellGraphics[][] cellGraphics, Graphics2D g2d) {
        this.cellGraphics = cellGraphics;
        this.g2d = g2d;
    }

    private void setWidth(int w) {
        this.width = w;
        int padding = -(getCellWidth() * 8 - this.width) / 2;
        corners[0] = new Point(padding, padding);
        corners[1] = new Point(w - padding, padding);
        corners[2] = new Point(w - padding, w - padding);
        corners[3] = new Point(padding, w - padding);
    }

    private void clearField() {
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

    private void drawGrid() {
        g2d.setStroke(new BasicStroke(Config.LINE_STROKE));
        g2d.setColor(Config.CELL_BRIGHT);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                cellGraphics[i][j].paint(getCellCenter(i, j), getCellWidth());
            }
        }
    }

    public void paint(int width) {
        setWidth(width);

        clearField();
        drawGrid();
    }
}
