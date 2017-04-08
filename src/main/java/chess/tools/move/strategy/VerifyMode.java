package chess.tools.move.strategy;

import chess.tools.game.Figure;
import chess.tools.model.BoardModel;
import chess.tools.move.Position;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public interface VerifyMode {

    default boolean verify(Position begin, Position end, BoardModel model) {
        final Figure current = model.getFigureOnBoard(begin);
        if (current.equals(Figure.EMPTY)) {
            return false;
        }
        final List<Position> positions = possibleFields(current, model);
        return !positions.isEmpty() && positions.contains(end)
                && !verifyOwnChess(begin, end, model, current);
    }

    default boolean verifyOwnChess(Position begin, Position end, BoardModel model, Figure current) {
        model.setFigureOnBoard(Figure.EMPTY, begin);
        final Figure endFigure = model.getFigureOnBoard(end);
        model.setFigureOnBoard(current, end);

        for (Figure figure : current.getColor().oppositColor().calcNotEaten()) {
            final List<Position> positions = figure.getMoveStrategy().getVerifyMode()
                    .possibleFields(figure, model);
            final List<Figure> figures = positions
                    .stream().map(model::getFigureOnBoard).collect(Collectors.toList());
            if (figures.stream().anyMatch(f -> f.equals(current.getColor().king()))) {
                model.setFigureOnBoard(current, begin);
                model.setFigureOnBoard(endFigure, end);
                Logger.getGlobal().warning("If you do that, your king will be chess.");
                return true;
            }
        }

        model.setFigureOnBoard(current, begin);
        model.setFigureOnBoard(endFigure, end);
        return false;
    }

    List<Position> possibleFields(Figure current, BoardModel model);
}
