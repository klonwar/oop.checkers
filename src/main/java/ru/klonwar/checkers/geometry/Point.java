package ru.klonwar.checkers.geometry;

import java.awt.event.MouseEvent;

public class Point implements Cloneable {
    public int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point addVector(Vector vector) {
        x += vector.x;
        y += vector.y;

        return this;
    }

    static public Point fromEvent(MouseEvent e) {
        return new Point(e.getX(), e.getY());
    }

    static public Point copy(Point point) {
        return new Point(point.x, point.y);
    }

    @Override
    public Point clone() {
        try {
            super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        return new Point(x, y);
    }
}
