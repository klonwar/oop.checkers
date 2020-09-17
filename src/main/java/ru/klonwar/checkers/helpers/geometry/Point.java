package ru.klonwar.checkers.helpers.geometry;

import java.awt.event.MouseEvent;

public class Point implements Cloneable {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point addVector(Vector vector) {
        x += vector.getX();
        y += vector.getY();

        return this;
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
