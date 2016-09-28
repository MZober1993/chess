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
    public boolean verify(Position begin, Position end, Figure[][] board) {
        final Figure current = Figure.figureForPos(board, begin);
        if (current.equals(Figure.EMPTY)) {
            return false;
        }
        List<Position> possiblePositions = new ArrayList<>();
        int endOffset = repeating ? 8 : 2;
        for (Direction dir : directions) {
            for (int i = 1; i < endOffset; i++) {
                final Position newPos = new Position(begin.getR() + i * dir.getR(), begin.getC() + i * dir.getC());
                System.out.println("newPos = " + newPos);
                if (newPos.isValid()) {
                    final Figure newFigure = Figure.figureForPos(board, newPos);
                    System.out.println("newFigure = " + newFigure + ", pos: " + newPos);
                    if (newFigure.equals(Figure.EMPTY)) {
                        possiblePositions.add(newPos);
                    } else if (current.isOppositeColor(newFigure)) {
                        possiblePositions.add(newPos);
                        break;
                    } else {
                        break;
                    }
                }
            }
        }
        System.out.println("possiblePositions = " + possiblePositions);
        return possiblePositions.contains(end);
    }
}
