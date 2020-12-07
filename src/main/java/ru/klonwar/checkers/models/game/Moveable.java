package ru.klonwar.checkers.models.game;

import ru.klonwar.checkers.util.Pair;
import ru.klonwar.checkers.util.Position;

import java.util.List;

public interface Moveable {

    /**
     * Возвращает два списка с позициями на доске. Те, в которые я мог бы пойти,
     * и в которые я обязан пойти. Приоритет стоит отдавать второму списку, если
     * он не пустой.
     * @param field Экземпляр <code>Field</code>, поле, в котором производится поиск
     * */

    Pair<List<Position>, List<Position>> findPossibleMoves(Field field);
}
