package chess.tools.move;

import chess.tools.game.Figure;

public class MoveTuple {
    private final Position begin;
    private final Position end;
    private final boolean possible;

    public MoveTuple(Position begin, Position end, boolean possible) {

        this.begin = begin;
        this.end = end;
        this.possible = possible;
    }

    public Position getBegin() {
        return begin;
    }

    public Position getEnd() {
        return end;
    }

    public boolean isPossible() {
        return possible;
    }

    public boolean rightColor(Figure[][] board, int turnCounter) {
        Figure oldBegin = board[begin.getC()][begin.getR()];
        return oldBegin.getColor().validateTurn(turnCounter);
    }

    @Override
    public String toString() {
        return "MoveTuple{" +
                "begin=" + begin +
                ", end=" + end +
                ", possible=" + possible +
                '}';
    }
}
