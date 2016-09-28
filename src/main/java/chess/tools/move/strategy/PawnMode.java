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
    public boolean verify(Position begin, Position end, Figure[][] board) {
        final Figure current = Figure.figureForPos(board, begin);
        if (current.equals(Figure.EMPTY)) {
            return false;
        }
        //TODO: chess-checking for the end-pos
        List<Position> possiblePositions = new ArrayList<>();
        for (Direction dir : directions) {
            final Position newPos = new Position(begin.getR() + dir.getR(), begin.getC() + dir.getC());
            System.out.println("newPos = " + newPos);
            if (newPos.isValid()) {
                final Figure newFigure = Figure.figureForPos(board, newPos);
                System.out.println("newFigure = " + newFigure + ", pos: " + newPos);
                if (dir.isAttackingMode()) {
                    if (current.isOppositeColor(newFigure)) {
                        possiblePositions.add(newPos);
                    }
                } else {
                    if (newFigure.equals(Figure.EMPTY)) {
                        possiblePositions.add(newPos);
                    }
                }

            }
        }
        System.out.println("possiblePositions = " + possiblePositions);
        return possiblePositions.contains(end);
    }
}
