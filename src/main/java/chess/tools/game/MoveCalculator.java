package chess.tools.game;

import chess.tools.move.Position;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MoveCalculator {

    public static Map<Figure, List<Position>> calcMoves(List<Figure> figures, Figure[][] board) {
        return figures.stream().collect(
                Collectors.toMap(Function.identity(), figure -> figure.possibleFields(board)));
    }
}
