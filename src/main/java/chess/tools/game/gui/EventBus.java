package chess.tools.game.gui;

import chess.tools.Chess;
import chess.tools.game.Figure;
import chess.tools.move.MoveTuple;
import chess.tools.move.Position;

import javax.swing.*;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public enum EventBus {
    CHESS;

    private Chess chess;
    private List<ChessSquare> chessSquares;
    private int chooseCounter = 0;
    private int registratedSquareIndex = -1;
    private Predicate<Integer> isBeginSquareIndex = i -> i != -1;
    private Predicate<Integer> isStraight = i -> i % 2 == 0;

    public void initGuiWithChess(JPanel chessBoard) {
        chessSquares = IntStream.range(0, 64).boxed().map(i -> new ChessSquare(i)).collect(Collectors.toList());
        chessSquares.forEach(chessBoard::add);

        chess = new Chess(false);
        showFiguresOnPanel(chess.getBoard());
    }

    private void showFiguresOnPanel(Figure[][] board) {
        IntStream.range(0, 64).parallel().forEach(i -> {
            final Position position = new Position(i);
            final Figure figure = board[position.getC()][position.getR()];
            if (!figure.equals(Figure.EMPTY)) {
                chessSquares.get(position.getIndex()).add(figure.getFigureLabel());
            }
        });
    }

    /**
     * @param square - chess square
     * @return colorate the chosen square- or not
     */
    public boolean registerSquare(ChessSquare square) {
        boolean moveValid = false;
        final Position currentPos = new Position(square.getIndex());
        final Figure currentFigure = chess.figureOnBoard(currentPos);
        final boolean validFigure = chess.colorValidation(currentPos) && !currentFigure.equals(Figure.EMPTY);

        if (isBeginSquareIndex.test(registratedSquareIndex) && isStraight.negate().test(chooseCounter)) {
            final Position begin = new Position(registratedSquareIndex);
            if (chess.figureOnBoard(begin).isOppositeColor(currentFigure)) {
                MoveTuple move = new MoveTuple(begin, currentPos, true);
                moveValid = chess.gameStep(move);
            } else if (validFigure) {
                final ChessSquare old = chessSquares.get(registratedSquareIndex);
                old.setChosen(false);
                old.setBackgroundToGeneral();
                chooseCounter = 0;
                registratedSquareIndex = square.getIndex();
            }
        } else if (validFigure) {
            registratedSquareIndex = square.getIndex();
        }
        final boolean isBegin = chooseCounter == 0;

        if ((isBegin && validFigure) || moveValid) {
            chooseCounter++;
        }

        if (isBeginSquareIndex.test(registratedSquareIndex) && isStraight.test(chooseCounter)) {
            realizeMoveForGUI(square);
        }

        return isBegin && validFigure;
    }

    private void realizeMoveForGUI(ChessSquare square) {
        final ChessSquare begin = chessSquares.get(registratedSquareIndex);
        final ChessSquare end = chessSquares.get(square.getIndex());
        begin.setBackgroundToGeneral();
        end.add(begin.getComponent(0));
        end.revalidate();
        if (end.getComponents().length == 2) {
            end.remove(0);
        }
        end.repaint();
        clearState(begin);
    }

    private void clearState(ChessSquare begin) {
        begin.setChosen(false);
        chooseCounter = 0;
        registratedSquareIndex = -1;
    }
}
