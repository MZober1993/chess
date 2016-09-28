package chess.tools.game;

import chess.tools.move.Position;
import chess.tools.move.strategy.MoveStrategy;

public enum Figure {

    BB(Color.BLACK, MoveStrategy.BB),
    TBA(Color.BLACK, MoveStrategy.T),
    SBB(Color.BLACK, MoveStrategy.S),
    LBC(Color.BLACK, MoveStrategy.L),
    DB(Color.BLACK, MoveStrategy.D),
    KB(Color.BLACK, MoveStrategy.K),
    LBF(Color.BLACK, MoveStrategy.L),
    SBG(Color.BLACK, MoveStrategy.S),
    TBH(Color.BLACK, MoveStrategy.T),
    BW(Color.WHITE, MoveStrategy.BW),
    TWA(Color.WHITE, MoveStrategy.T),
    SWB(Color.WHITE, MoveStrategy.S),
    LWC(Color.WHITE, MoveStrategy.L),
    DW(Color.WHITE, MoveStrategy.D),
    KW(Color.WHITE, MoveStrategy.K),
    LWF(Color.WHITE, MoveStrategy.L),
    SWG(Color.WHITE, MoveStrategy.S),
    TWH(Color.WHITE, MoveStrategy.T),
    EMPTY(Color.EMPTY, MoveStrategy.EMPTY);

    private final Color color;
    private final MoveStrategy moveStrategy;
    private final char term;

    Figure(Color color, MoveStrategy moveStrategy) {
        if (color.equals(Color.EMPTY)) {
            this.term = '.';
        } else {
            this.term = this.name().charAt(0);
        }
        this.color = color;
        this.moveStrategy = moveStrategy;
    }

    public Color getColor() {
        return color;
    }

    public MoveStrategy getMoveStrategy() {
        return moveStrategy;
    }

    public char getTerm() {
        return term;
    }

    public boolean verifyMove(Position begin, Position end, Figure[][] board) {
        return this.moveStrategy.verify(begin, end, board);
    }

    public boolean isOppositeColor(Figure opposite) {
        return !this.color.equals(opposite.color);
    }

    public static Figure figureForPos(Figure[][] board, Position position) {
        return board[position.getC()][position.getR()];
    }

    @Override
    public String toString() {
        return String.valueOf(term);
    }
}
