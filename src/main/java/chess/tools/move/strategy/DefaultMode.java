package chess.tools.move.strategy;

import chess.tools.game.Chess;
import chess.tools.game.Figure;
import chess.tools.move.Direction;
import chess.tools.move.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultMode implements VerifyMode {

    private final boolean repeating;
    private final List<Direction> directions;

    DefaultMode(boolean repeating, List<Direction> directions) {
        this.repeating = repeating;
        this.directions = directions;
    }

    @Override
    public List<Position> possibleFields(Figure current, Position begin, Figure[][] board) {
        List<Position> possibleFields = new ArrayList<>();
        int endOffset = repeating ? 8 : 2;
        for (Direction dir : directions) {
            for (int i = 1; i < endOffset; i++) {
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
        System.out.println(possibleFields.stream().map(pos -> Chess.MAPPING.getCommandMapping().get(pos))
                .collect(Collectors.toList()));
        return possibleFields;
    }
}
