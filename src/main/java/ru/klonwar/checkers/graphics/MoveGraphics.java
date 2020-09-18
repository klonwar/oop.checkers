package ru.klonwar.checkers.graphics;

import ru.klonwar.checkers.config.ColorEnum;
import ru.klonwar.checkers.config.Config;
import ru.klonwar.checkers.helpers.geometry.Point;
import ru.klonwar.checkers.helpers.geometry.Vector;
import ru.klonwar.checkers.models.Cell;
import ru.klonwar.checkers.models.Checker;

import java.awt.*;

public class MoveGraphics {
    public void paint(Graphics2D g2d, int color, Point point, boolean gameOver) {
        g2d.setStroke(new BasicStroke(Config.LINE_STROKE));
        g2d.setColor(ColorEnum.BLACK.getColor());
        g2d.setFont(Config.FONT);
        g2d.drawString((gameOver) ? "Победил:" : "Ходит:", point.getX(), point.getY() + Config.FONT_SIZE);

        CheckerGraphics activePlayerChecker = new CheckerGraphics(new Cell(new Checker(color)));
        int r = Config.FONT_SIZE;
        activePlayerChecker.paint(new Point(point.getX() + r, point.getY() + 5 * r / 2), r, g2d);

        if (gameOver) {
            int size = point.getX();
            Point center = point.clone().addVector(new Vector(-size / 2, -size / 2));

            g2d.setColor(ColorEnum.BACKGROUND.getColor());
            MyGraphics.drawSquareWithCenter(g2d, center, size);

            g2d.setColor(ColorEnum.BLACK.getColor());
            g2d.setFont(Config.FONT);
            g2d.drawString("Игра окончена", 0, Config.FONT_SIZE);
        }
    }
}
