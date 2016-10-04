package chess.tools.move.strategy;

import chess.tools.game.ChessColor;
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
        final List<Position> positions = possibleFields(current, begin, board);
        return !positions.isEmpty() && positions.contains(end)
                && !chessCheck(begin, end, board, current);
    }

    default boolean chessCheck(Position begin, Position end, Figure[][] board, Figure current) {
        //simulate turn
        board[begin.getC()][begin.getR()] = Figure.EMPTY;
        final Figure endFigure = board[end.getC()][end.getR()];
        board[end.getC()][end.getR()] = current;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Figure figure = board[i][j];
                if (!figure.getColor().equals(ChessColor.EMPTY)
                        && figure.isOppositeColor(current)
                        ) {
                    if (figure.equals(Figure.LBF)) {
                        System.out.println("L here");
                    }

                    final List<Position> positions = figure.getMoveStrategy().getVerifyMode()
                            .possibleFields(figure, new Position(i, j), board);
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
            }
        }

        board[begin.getC()][begin.getR()] = current;
        board[end.getC()][end.getR()] = endFigure;
        return false;
    }

    List<Position> possibleFields(Figure current, Position begin, Figure[][] board);
}
