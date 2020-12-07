package ru.klonwar.checkers.graphics;

import ru.klonwar.checkers.util.geometry.Point;

import java.awt.*;

public final class MyGraphics {
    private MyGraphics() {}

    public static void drawLine(Graphics2D g2d, ru.klonwar.checkers.util.geometry.Point start, ru.klonwar.checkers.util.geometry.Point end) {
        g2d.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
    }

    public static void drawSquareWithCenter(Graphics2D g2d, ru.klonwar.checkers.util.geometry.Point center, int w) {
        g2d.drawRect(center.getX() - w/2, center.getY() - w/2, w, w);
    }

    public static void fillSquareWithCenter(Graphics2D g2d, ru.klonwar.checkers.util.geometry.Point center, int w) {
        g2d.fillRect(center.getX() - w/2, center.getY() - w/2, w, w);
    }

    public static void drawRectWithCenter(Graphics2D g2d, Point center, int w, int h) {
        g2d.drawRect(center.getX() - w/2, center.getY() - h/2, w, h);
    }

    public static void fillRectWithCenter(Graphics2D g2d, Point center, int w, int h) {
        g2d.fillRect(center.getX() - w/2, center.getY() - h/2, w, h);
    }

    public static void drawCircleWithCenter(Graphics2D g2d, ru.klonwar.checkers.util.geometry.Point center, int r) {
        g2d.drawOval(center.getX() - r, center.getY() - r, r * 2, r * 2);
    }

    public static void fillCircleWithCenter(Graphics2D g2d, Point center, int r) {
        g2d.fillOval(center.getX() - r, center.getY() - r, r * 2, r * 2);
    }

    public static void fillArcWithCenter(Graphics2D g2d, Point center, int r, int startAngle, int endAngle) {
        g2d.fillArc(center.getX() - r, center.getY() - r, r * 2, r * 2, startAngle, endAngle);
    }
}
