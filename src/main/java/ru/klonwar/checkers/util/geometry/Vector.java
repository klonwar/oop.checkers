package ru.klonwar.checkers.util.geometry;

public class Vector extends Point {
    public Vector(int x, int y) {
        super(x, y);
    }

    public Vector(Point start, Point end) {
        super(end.getX() - start.getX(), end.getY() - start.getY());
    }

    @Override
    public Point clone() {
        return super.clone();
    }
}
