package ru.klonwar.checkers.models.game;

import org.junit.Assert;
import org.junit.Test;
import ru.klonwar.checkers.util.Pair;
import ru.klonwar.checkers.util.Position;

import java.util.ArrayList;
import java.util.List;

public class CheckerTest {
    private void setEmptyState(Field field) {
        Cell[][] state = new Cell[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                state[i][j] = new Cell(null);
            }
        }
        field.setFieldState(state);
    }

    private List<Position> getMovesFromCheckerPosition(Field field, Position position) {
        Checker checker = field.getCellFromPosition(position).getChecker();
        if (checker == null) {
            return null;
        }

        Pair<List<Position>, List<Position>> movesPair = checker.findPossibleMoves(field);
        return (movesPair.getSecond() != null && movesPair.getSecond().size() != 0) ? movesPair.getSecond() : movesPair.getFirst();
    }

    @Test
    public void possibleMovesDetectingCorrectlyWhenBattle() {
        Field field = new Field();

        setEmptyState(field);
        field.getFieldState()[4][4] = new Cell(new Checker(PlayerColor.WHITE));
        field.getFieldState()[3][3] = new Cell(new Checker(PlayerColor.BLACK));

        Assert.assertEquals(1, getMovesFromCheckerPosition(field, new Position(4, 4)).size());
        Assert.assertEquals(1, getMovesFromCheckerPosition(field, new Position(4, 4)).size());
        Assert.assertEquals((new Position(5, 5)), getMovesFromCheckerPosition(field, new Position(3, 3)).get(0));
        Assert.assertEquals((new Position(5, 5)), getMovesFromCheckerPosition(field, new Position(3, 3)).get(0));

    }

    @Test
    public void possibleMovesDetectingCorrectlyWhenKingBattle() {
        Field field = new Field();

        setEmptyState(field);
        field.getFieldState()[4][4] = new Cell(new Checker(PlayerColor.WHITE));
        Checker king = new Checker(PlayerColor.BLACK);
        king.becomeKing();
        field.getFieldState()[0][0] = new Cell(king);

        Assert.assertNotEquals(0, getMovesFromCheckerPosition(field, new Position(0, 0)).size());
        Assert.assertEquals(2, getMovesFromCheckerPosition(field, new Position(4, 4)).size());
        Assert.assertEquals((new Position(5, 5)), getMovesFromCheckerPosition(field, new Position(0, 0)).get(0));

    }

    @Test
    public void possibleMovesDetectingCorrectlyAtStartState() {
        Field field = new Field();
        List<Position> moves;

        moves = getMovesFromCheckerPosition(field, new Position(0, 0));
        Assert.assertNull(moves);

        moves = getMovesFromCheckerPosition(field, new Position(0, 1));
        Assert.assertEquals(new ArrayList<>(), moves);

        moves = getMovesFromCheckerPosition(field, new Position(0, 5));
        Assert.assertNotNull(moves);
        Assert.assertEquals(1, moves.size());

        moves = getMovesFromCheckerPosition(field, new Position(2, 5));
        Assert.assertNotNull(moves);
        Assert.assertEquals(2, moves.size());
    }
}
