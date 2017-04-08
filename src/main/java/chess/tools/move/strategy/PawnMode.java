package chess.tools.move.strategy;

import chess.tools.game.Figure;
import chess.tools.model.BoardModel;
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
    public List<Position> possibleFields(Figure current, BoardModel model) {
        List<Position> possibleFields = new ArrayList<>();
        Position begin = current.getPosition();
        for (Direction dir : directions) {
            final Position newPos = new Position(begin.getColumn() + dir.getColumn(), begin.getRow() + dir.getRow());

            if (newPos.isValid() && !possibleFields.contains(newPos)) {
                final Figure newFigure = model.getFigureOnBoard(newPos);
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
