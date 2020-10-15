package ru.klonwar.checkers.models;

import ru.klonwar.checkers.helpers.Pair;
import ru.klonwar.checkers.helpers.Position;

import java.util.ArrayList;
import java.util.List;

public class King extends Checker implements Moveable {
    public King(int color) {
        super(color);
    }

    @Override
    public boolean canBecomeKing(Position checkerPosition) {
        return false;
    }

    @Override
    public Pair<List<Position>, List<Position>> findPossibleMoves(Field field) {
        List<Position> possible = new ArrayList<>();
        List<Position> required = new ArrayList<>();
        Position now = field.getPositionFromChecker(this);

        for (int i = -1; i <= 1; i += 2) {
            for (int j = -1; j <= 1; j += 2) {
                boolean evilCheckerReached = false;
                Position target = new Position(now.getFirst(), now.getSecond());
                while (true) {
                    target = new Position(target.getFirst() + i, target.getSecond() + j);
                    Cell targetCell = field.getCellFromPosition(target);
                    // Ячейки на этой позиции нет
                    if (targetCell == null) break;

                    Checker targetChecker = targetCell.getChecker();
                    if (targetChecker == null) {
                        // Ячейка свободна
                        possible.add(target);
                        if (evilCheckerReached) {
                            required.add(target);
                        }
                    } else if (this.getColor() != targetChecker.getColor()) {
                        // В ячейке есть шашка противника
                        // Смотрим на следующую
                        target = new Position(target.getFirst() + i, target.getSecond() + j);
                        targetCell = field.getCellFromPosition(target);
                        if (targetCell == null) break;

                        targetChecker = targetCell.getChecker();
                        if (targetChecker == null) {
                            // В следующей - пусто
                            required.add(target);
                            evilCheckerReached = true;
                        } else {
                            // Занято
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
        }

        return new Pair<>(possible, required);
    }
}
