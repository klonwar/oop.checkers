package ru.klonwar.checkers.geometry;

public class Vector extends Point {
    public Vector(int x, int y) {
        super(x, y);
    }

    public Vector(Point start, Point end) {
        super(end.x - start.x, end.y - start.y);
    }

    @Override
    public Point clone() {
        super.clone();

        return new Vector(x, y);
    }
}
