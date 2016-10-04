package chess.tools.move.strategy;

import chess.tools.game.Figure;
import chess.tools.move.Position;

import java.util.List;
import java.util.stream.Collectors;

public interface VerifyMode {

    default boolean verify(Position begin, Position end, Figure[][] board) {
        final Figure current = Figure.figureForPos(board, begin);
        if (current.equals(Figure.EMPTY)) {
            return false;
        }
        final List<Position> positions = possibleFields(current, board);
        return !positions.isEmpty() && positions.contains(end)
                && !verifyOwnChess(begin, end, board, current);
    }

    default boolean verifyOwnChess(Position begin, Position end, Figure[][] board, Figure current) {
        //simulate turn
        board[begin.getC()][begin.getR()] = Figure.EMPTY;
        final Figure endFigure = board[end.getC()][end.getR()];
        board[end.getC()][end.getR()] = current;

        for (Figure figure : current.getColor().oppositColor().allFromColor()) {
            final List<Position> positions = figure.getMoveStrategy().getVerifyMode()
                    .possibleFields(figure, board);
            final List<Figure> figures = positions
                    .stream().map(pos -> Figure.figureForPos(board, pos)).collect(Collectors.toList());
            System.out.println(figure.getColor() + " " + figure + " found figures = " + figures);
            System.out.println();
            if (figures.stream().anyMatch(f -> f.equals(current.getColor().king()))) {
                board[begin.getC()][begin.getR()] = current;
                board[end.getC()][end.getR()] = endFigure;
                System.out.println("If you do that, your king will be chess.");
                return true;
            }
        }

        board[begin.getC()][begin.getR()] = current;
        board[end.getC()][end.getR()] = endFigure;
        return false;
    }

    List<Position> possibleFields(Figure current, Figure[][] board);
}
