package ru.klonwar.checkers.figures;

import ru.klonwar.checkers.config.Config;
import ru.klonwar.checkers.geometry.MyGraphics;
import ru.klonwar.checkers.geometry.Point;

import java.awt.*;

public class CheckerGraphics {
    Graphics2D g2d;
    Color color;

    public CheckerGraphics(Color color, Graphics2D g2d) {
        this.g2d = g2d;
        this.color = color;
    }

    public void paint(Point center, int radius) {
        g2d.setColor(color);
        MyGraphics.fillCircleWithCenter(g2d, center, radius);
    }
}
