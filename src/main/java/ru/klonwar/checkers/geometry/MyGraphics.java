package ru.klonwar.checkers.geometry;

import java.awt.*;

public final class MyGraphics {
    private MyGraphics() {}

    public static void drawLine(Graphics2D g2d, Point start, Point end) {
        g2d.drawLine(start.x, start.y, end.x, end.y);
    }

    public static void drawSquareWithCenter(Graphics2D g2d, Point center, int w) {
        g2d.drawRect(center.x - w/2, center.y - w/2, w, w);
    }

    public static void fillSquareWithCenter(Graphics2D g2d, Point center, int w) {
        g2d.fillRect(center.x - w/2, center.y - w/2, w, w);
    }

    public static void drawCircleWithCenter(Graphics2D g2d, Point center, int r) {
        g2d.drawOval(center.x - r, center.y - r, r * 2, r * 2);
    }

    public static void fillCircleWithCenter(Graphics2D g2d, Point center, int r) {
        g2d.fillOval(center.x - r, center.y - r, r * 2, r * 2);
    }
}
