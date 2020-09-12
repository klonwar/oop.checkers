package ru.klonwar.checkers.cell;

import ru.klonwar.checkers.config.Config;
import ru.klonwar.checkers.figures.CheckerGraphics;
import ru.klonwar.checkers.geometry.MyGraphics;
import ru.klonwar.checkers.geometry.Point;
import ru.klonwar.checkers.geometry.Vector;

import java.awt.*;

public class CellGraphics {
    CheckerGraphics checkerGraphics;
    Point leftTop = new Point(0, 0);
    Point rightBot = new Point(0, 0);
    int width;
    Graphics2D g2d;
    Color cellColor;

    public CellGraphics(CheckerGraphics checkerGraphics, Color cellColor, Graphics2D g2d) {
        this.checkerGraphics = checkerGraphics;
        this.cellColor = cellColor;
        this.g2d = g2d;
    }

    public boolean isClicked(int x, int y) {
        return (x >= leftTop.x && x <= rightBot.x && y >= leftTop.y && y <= rightBot.y);
    }

    public void paint(Point center, int width) {
        this.width = width;

        leftTop = center.clone().addVector(new Vector(-1 * (width) / 2, -1 * (width) / 2));
        rightBot = center.clone().addVector(new Vector(width / 2, width / 2));

        g2d.setColor(cellColor);
        MyGraphics.fillSquareWithCenter(g2d, center, width);

        if (this.checkerGraphics != null) {
            this.checkerGraphics.paint(center, 2 * width / 5);
        }
    }
}
