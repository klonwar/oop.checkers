package ru.klonwar.checkers.graphics;

import ru.klonwar.checkers.config.ColorEnum;
import ru.klonwar.checkers.helpers.geometry.Point;
import ru.klonwar.checkers.models.Cell;
import ru.klonwar.checkers.models.Checker;
import ru.klonwar.checkers.models.King;

import java.awt.*;

public class CheckerGraphics {
    private final Cell cell;

    public CheckerGraphics(Cell cell) {
        this.cell = cell;
    }

    public void paint(Point center, int radius, Graphics2D g2d) {
        if (cell.getChecker() != null) {
            g2d.setColor((cell.getChecker().getColor() == 0) ? ColorEnum.BLACK_CHECKER.getColor() : ColorEnum.WHITE_CHECKER.getColor());
            MyGraphics.fillCircleWithCenter(g2d, center, radius);

            if (cell.getChecker() instanceof King) {
                g2d.setColor(ColorEnum.KING_ACCENT.getColor());
                MyGraphics.fillArcWithCenter(g2d, center, radius, 45, -4*45);
            }
        }
    }
}
