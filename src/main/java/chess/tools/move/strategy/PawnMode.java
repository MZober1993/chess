package chess.tools.move.strategy;

import chess.tools.game.Figure;
import chess.tools.move.Direction;
import chess.tools.move.Position;

import java.util.ArrayList;
import java.util.List;

public class PawnMode implements VerifyMode {

    private final List<Direction> directions;

    PawnMode(List<Direction> directions) {
        this.directions = directions;
    }

    @Override
    public List<Position> possibleFields(Figure current, Figure[][] board) {
        List<Position> possibleFields = new ArrayList<>();
        Position begin = current.getPosition();
        for (Direction dir : directions) {
            final Position newPos = new Position(begin.getC() + dir.getC(), begin.getR() + dir.getR());

            if (newPos.isValid() && !possibleFields.contains(newPos)) {
                final Figure newFigure = Figure.figureForPos(board, newPos);
                if (dir.isAttackingMode()) {
                    if (current.isOppositeColor(newFigure) && !newFigure.equals(Figure.EMPTY)) {
                        possibleFields.add(newPos);
                    }
                } else {
                    if (newFigure.equals(Figure.EMPTY)) {
                        possibleFields.add(newPos);
                    }
                }
            }
        }
        return possibleFields;
    }
}
