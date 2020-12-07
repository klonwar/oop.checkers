package ru.klonwar.checkers.models.game;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import ru.klonwar.checkers.helpers.Pair;
import ru.klonwar.checkers.helpers.Position;

import java.util.ArrayList;
import java.util.List;

public class Checker implements Moveable, Colored {
    private int color;
    @JsonProperty("king")
    private boolean isKing = false;

    public boolean isKing() {
        return isKing;
    }

    public void becomeKing() {
        isKing = true;
    }

    public Checker() {
    }

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

        if (isKing())
            kingMoves(possible, required, field);
        else
            checkerMoves(possible, required, field);

        return new Pair<>(possible, required);
    }

    private void checkerMoves(List<Position> possible, List<Position> required, Field field) {
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
    }

    private void kingMoves(List<Position> possible, List<Position> required, Field field) {
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
    }

    public boolean canBecomeKing(Position checkerPosition) {
        if (isKing())
            return false;
        return (color == 1) ? checkerPosition.getSecond() == 0 : checkerPosition.getSecond() == 7;
    }

    @Override
    public String toString() {
        return (color == 0) ? "o" : "@";
    }
}