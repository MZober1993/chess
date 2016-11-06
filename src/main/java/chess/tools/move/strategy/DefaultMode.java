package chess.tools.move.strategy;

import chess.tools.game.Figure;
import chess.tools.move.Direction;
import chess.tools.move.Position;

import java.util.ArrayList;
import java.util.List;

public class DefaultMode implements VerifyMode {

    private final boolean repeating;
    private final List<Direction> directions;

    DefaultMode(boolean repeating, List<Direction> directions) {
        this.repeating = repeating;
        this.directions = directions;
    }

    @Override
    public List<Position> possibleFields(Figure current, Figure[][] board) {
        List<Position> possibleFields = new ArrayList<>();
        Position begin = current.getPosition();
        int endOffset = repeating ? 8 : 2;
        for (Direction dir : directions) {
            for (int i = 1; i < endOffset; i++) {
                if (begin.getC() + i * dir.getC() < 0 || begin.getR() + i * dir.getR() < 0) {
                    continue;
                }
                final Position newPos = new Position(begin.getC() + i * dir.getC(), begin.getR() + i * dir.getR());
                if (newPos.isValid() && !possibleFields.contains(newPos)) {
                    final Figure newFigure = Figure.figureForPos(board, newPos);
                    if (newFigure.equals(Figure.EMPTY)) {
                        possibleFields.add(newPos);
                    } else if (current.isOppositeColor(newFigure)) {
                        possibleFields.add(newPos);
                        break;
                    } else {
                        break;
                    }
                }
            }
        }
        return possibleFields;
    }
}
