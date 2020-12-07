package ru.klonwar.checkers.graphics;

import ru.klonwar.checkers.config.ColorEnum;
import ru.klonwar.checkers.config.Config;
import ru.klonwar.checkers.util.geometry.Point;
import ru.klonwar.checkers.util.geometry.Vector;
import ru.klonwar.checkers.models.game.Cell;
import ru.klonwar.checkers.models.game.Checker;
import ru.klonwar.checkers.models.game.Player;

import java.awt.*;

public class MoveGraphics {
    public void paint(Graphics2D g2d, Player player, Point point, boolean gameOver) {
        g2d.setStroke(new BasicStroke(Config.LINE_STROKE));
        g2d.setColor(ColorEnum.BLACK.getColor());
        g2d.setFont(Config.FONT);
        g2d.drawString(((gameOver) ? "Победил:" : "Ходит:") + player.getLogin(), point.getX(), point.getY() + Config.FONT_SIZE);

        CellGraphics activePlayerChecker = new CellGraphics(new Cell(new Checker(player.getColor())), ColorEnum.BACKGROUND.getColor());
        int r = Config.FONT_SIZE;
        activePlayerChecker.paint(g2d, new Point(point.getX() + r, point.getY() + 5 * r / 2), r*2, false, false, false);

        g2d.drawString("Ctrl + R - Заново", point.getX(), point.getY() + 8*Config.FONT_SIZE);
        g2d.drawString("Esc - Сбросить выделение", point.getX(), point.getY() + 9*Config.FONT_SIZE);
        g2d.drawString("F1 - Страница статистики", point.getX(), point.getY() + 10*Config.FONT_SIZE);

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
