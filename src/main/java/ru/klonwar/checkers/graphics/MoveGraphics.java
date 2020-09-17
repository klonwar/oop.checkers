package ru.klonwar.checkers.graphics;

import ru.klonwar.checkers.config.ColorEnum;
import ru.klonwar.checkers.config.Config;
import ru.klonwar.checkers.helpers.geometry.Point;
import ru.klonwar.checkers.models.Cell;
import ru.klonwar.checkers.models.Checker;

import java.awt.*;

public class MoveGraphics {
    public void paint(Graphics2D g2d, int color, Point point) {
        g2d.setStroke(new BasicStroke(Config.LINE_STROKE));
        g2d.setColor(ColorEnum.BLACK.getColor());
        g2d.setFont(Config.FONT);
        g2d.drawString("Ходит:", point.getX(), point.getY() + Config.FONT_SIZE);

        CheckerGraphics activePlayerChecker = new CheckerGraphics(new Cell(new Checker(color)));
        int r = Config.FONT_SIZE;
        activePlayerChecker.paint(new Point(point.getX() + r, point.getY() + 5 * r / 2), r, g2d);
    }
}
