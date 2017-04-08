package chess.tools.move;

import chess.tools.game.Figure;
import chess.tools.model.BoardModel;

public class MoveTuple {
    private final Position begin;
    private final Position end;
    private final boolean possible;
    private Figure figure = Figure.EMPTY;

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

    public Figure getFigure() {
        return figure;
    }

    public void setFigure(Figure figure) {
        this.figure = figure;
    }

    public boolean rightColor(BoardModel model, int turnCounter) {
        Figure oldBegin = model.getFigureOnBoard(begin);
        return oldBegin.getColor().validateTurn(turnCounter);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MoveTuple moveTuple = (MoveTuple) o;

        return possible == moveTuple.possible && begin.equals(moveTuple.begin) && end.equals(moveTuple.end);

    }

    @Override
    public int hashCode() {
        int result = begin.hashCode();
        result = 31 * result + end.hashCode();
        result = 31 * result + (possible ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "(" + begin +
                ")->(" + end +
                ")";
    }
}
