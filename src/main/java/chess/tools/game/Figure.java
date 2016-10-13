package chess.tools.game;

import chess.tools.move.Position;
import chess.tools.move.strategy.MoveStrategy;

import java.util.List;

public enum Figure {

    TBA(ChessColor.BLACK, MoveStrategy.T),
    SBB(ChessColor.BLACK, MoveStrategy.S),
    LBC(ChessColor.BLACK, MoveStrategy.L),
    DB(ChessColor.BLACK, MoveStrategy.D),
    KB(ChessColor.BLACK, MoveStrategy.K),
    LBF(ChessColor.BLACK, MoveStrategy.L),
    SBG(ChessColor.BLACK, MoveStrategy.S),
    TBH(ChessColor.BLACK, MoveStrategy.T),
    BBA(ChessColor.BLACK, MoveStrategy.BB),
    BBB(ChessColor.BLACK, MoveStrategy.BB),
    BBC(ChessColor.BLACK, MoveStrategy.BB),
    BBD(ChessColor.BLACK, MoveStrategy.BB),
    BBE(ChessColor.BLACK, MoveStrategy.BB),
    BBF(ChessColor.BLACK, MoveStrategy.BB),
    BBG(ChessColor.BLACK, MoveStrategy.BB),
    BBH(ChessColor.BLACK, MoveStrategy.BB),
    TWA(ChessColor.WHITE, MoveStrategy.T),
    SWB(ChessColor.WHITE, MoveStrategy.S),
    LWC(ChessColor.WHITE, MoveStrategy.L),
    DW(ChessColor.WHITE, MoveStrategy.D),
    KW(ChessColor.WHITE, MoveStrategy.K),
    LWF(ChessColor.WHITE, MoveStrategy.L),
    SWG(ChessColor.WHITE, MoveStrategy.S),
    TWH(ChessColor.WHITE, MoveStrategy.T),
    BWA(ChessColor.WHITE, MoveStrategy.BW),
    BWB(ChessColor.WHITE, MoveStrategy.BW),
    BWC(ChessColor.WHITE, MoveStrategy.BW),
    BWD(ChessColor.WHITE, MoveStrategy.BW),
    BWE(ChessColor.WHITE, MoveStrategy.BW),
    BWF(ChessColor.WHITE, MoveStrategy.BW),
    BWG(ChessColor.WHITE, MoveStrategy.BW),
    BWH(ChessColor.WHITE, MoveStrategy.BW),
    EMPTY(ChessColor.EMPTY, MoveStrategy.EMPTY);

    private final ChessColor color;
    private final MoveStrategy moveStrategy;
    private final char term;
    private Position position = new Position(0, 0);
    private boolean eaten = false;

    Figure(ChessColor color, MoveStrategy moveStrategy) {
        if (color.equals(ChessColor.EMPTY)) {
            this.term = '.';
        } else {
            this.term = this.name().charAt(0);
        }
        this.color = color;
        this.moveStrategy = moveStrategy;
    }

    public ChessColor getColor() {
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

    public String moreInfoString() {
        return color + " " + toString();
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public List<Position> possibleFields(Figure[][] board) {
        return getMoveStrategy().getVerifyMode().possibleFields(this, board);
    }

    public boolean isEaten() {
        return eaten;
    }

    public void setEaten(boolean eaten) {
        this.eaten = eaten;
    }
}
