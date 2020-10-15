package ru.klonwar.checkers.models;

import ru.klonwar.checkers.helpers.Pair;
import ru.klonwar.checkers.helpers.Position;

import java.util.ArrayList;
import java.util.List;

public class Checker implements Moveable, Colored {
    private final int color;

    public Checker(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public boolean eatEverything(Field field, Position from, Position to) {
        boolean endOfTurn = true;

        int deltaH = Math.round(Math.signum(to.getFirst() - from.getFirst()));
        int deltaV = Math.round(Math.signum(to.getSecond() - from.getSecond()));

        Cell toCell;
        Cell fromCell = field.getCellFromPosition(from);

        if (deltaH != 0 || deltaV != 0) {
            for (
                // i, j - координаты следующей ячейки, в которую мы передвинем шашку
                    int i = from.getFirst() + deltaH, j = from.getSecond() + deltaV;
                // Выполняем, пока не достигнем точки назначения
                    ((deltaH >= 0) ? i <= to.getFirst() : i >= to.getFirst()) || ((deltaV >= 0) ? j <= to.getSecond() : j >= to.getSecond());
                // Будем передвигать шашку в каждую следующую ячейку
                    i += deltaH, j += deltaV
            ) {
                toCell = field.getCellFromPosition(new Position(i, j));

                if (toCell == null || fromCell == null) return true;

                if (toCell.getChecker() != null) {
                    // Что-то съели
                    endOfTurn = false;
                }

                toCell.setChecker(this);
                fromCell.setChecker(null);

                fromCell = toCell;
            }
        }
        return endOfTurn;
    }

    public Pair<List<Position>, List<Position>> findPossibleMoves(Field field) {
        List<Position> possible = new ArrayList<>();
        List<Position> required = new ArrayList<>();
        Position now = field.getPositionFromChecker(this);

        for (int i = -1; i <= 1; i += 2) {
            for (int j = -1; j <= 1; j += 2) {
                Position target = new Position(now.getFirst() + i, now.getSecond() + j);
                Cell targetCell = field.getCellFromPosition(target);
                // Ячейки на этой позиции нет
                if (targetCell == null) continue;

                Checker targetChecker = targetCell.getChecker();
                if (targetChecker == null && (color == 0 && j > 0 || color == 1 && j < 0)) {
                    // Ячейка свободна, и шашка текущего цвета может идти в ее направлении
                    possible.add(target);
                } else if (targetChecker != null && color != targetChecker.getColor()) {
                    // В ячейке есть шашка противника, в следующей - пусто
                    target = new Position(now.getFirst() + 2 * i, now.getSecond() + 2 * j);
                    targetCell = field.getCellFromPosition(target);
                    if (targetCell == null) continue;

                    targetChecker = targetCell.getChecker();
                    if (targetChecker == null) {
                        required.add(target);

                    }
                }
            }
        }


        return new Pair<>(possible, required);
    }

    public boolean canBecomeKing(Position checkerPosition) {
        return (color == 1) ? checkerPosition.getSecond() == 0 : checkerPosition.getSecond() == 7;
    }

    @Override
    public String toString() {
        return (color == 0) ? "o" : "@";
    }
}
