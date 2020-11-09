package ru.klonwar.checkers.models.game;

import org.junit.Assert;
import org.junit.Test;
import ru.klonwar.checkers.helpers.Position;
import ru.klonwar.checkers.models.game.Cell;
import ru.klonwar.checkers.models.game.Checker;
import ru.klonwar.checkers.models.game.Field;

public class FieldTest {

    @Test
    public void positionWorksCorrectly() {
        Field field = new Field();

        Assert.assertNull(field.getCellFromPosition(new Position(-1,-1)));
        Assert.assertEquals(field.getFieldState()[0][0], field.getCellFromPosition(new Position(0,0)));
        Assert.assertEquals(field.getFieldState()[0][1], field.getCellFromPosition(new Position(0,1)));
        Assert.assertEquals(field.getFieldState()[1][0], field.getCellFromPosition(new Position(1,0)));

        Assert.assertNull(field.getPositionFromCell(new Cell(new Checker(0))));
        Assert.assertEquals(new Position(0, 0), field.getPositionFromCell(field.getFieldState()[0][0]));
        Assert.assertEquals(new Position(0, 1), field.getPositionFromCell(field.getFieldState()[0][1]));
        Assert.assertEquals(new Position(1, 0), field.getPositionFromCell(field.getFieldState()[1][0]));

        Assert.assertNull(field.getPositionFromChecker(new Checker(0)));
        Assert.assertEquals(new Position(0, 0), field.getPositionFromCell(field.getFieldState()[0][0]));
        Assert.assertEquals(new Position(0, 1), field.getPositionFromCell(field.getFieldState()[0][1]));
        Assert.assertEquals(new Position(1, 0), field.getPositionFromCell(field.getFieldState()[1][0]));
    }

    @Test
    public void isInFieldWorksCorrectly() {
        Assert.assertFalse(Field.isInField(new Position(-1, -1)));
        Assert.assertFalse(Field.isInField(new Position(Field.width + 1, Field.height + 1)));
        Assert.assertTrue(Field.isInField(new Position(1,1)));
    }

}
