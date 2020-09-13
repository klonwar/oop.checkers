package ru.klonwar.checkers.graphics;

import ru.klonwar.checkers.config.Config;
import ru.klonwar.checkers.helpers.geometry.Point;
import ru.klonwar.checkers.models.Cell;
import ru.klonwar.checkers.models.Checker;

import java.awt.*;

public class CheckerController {
    private final Cell cell;

    public CheckerController(Cell cell) {
        this.cell = cell;
    }

    public void paint(Point center, int radius, Graphics2D g2d) {
        Checker checker = cell.getChecker();
        if (checker != null) {
            g2d.setColor((checker.getColor() == 0) ? Config.BLACK_CHECKER : Config.WHITE_CHECKER);
            MyGraphics.fillCircleWithCenter(g2d, center, radius);
        }
    }
}
