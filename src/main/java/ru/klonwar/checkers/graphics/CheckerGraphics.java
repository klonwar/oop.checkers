package ru.klonwar.checkers.graphics;

import ru.klonwar.checkers.config.ColorEnum;
import ru.klonwar.checkers.config.Config;
import ru.klonwar.checkers.helpers.geometry.Point;
import ru.klonwar.checkers.models.Cell;
import ru.klonwar.checkers.models.Checker;

import java.awt.*;

public class CheckerGraphics {
    private final Cell cell;

    public CheckerGraphics(Cell cell) {
        this.cell = cell;
    }

    public void paint(Point center, int radius, Graphics2D g2d) {
        Checker checker = cell.getChecker();
        if (checker != null) {
            g2d.setColor((checker.getColor() == 0) ? ColorEnum.BLACK_CHECKER.getColor() : ColorEnum.WHITE_CHECKER.getColor());
            MyGraphics.fillCircleWithCenter(g2d, center, radius);
        }
    }
}
