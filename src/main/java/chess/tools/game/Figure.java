package chess.tools.game;

import chess.tools.game.gui.FigureLabel;
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
    BB(ChessColor.BLACK, MoveStrategy.BB),
    TWA(ChessColor.WHITE, MoveStrategy.T),
    SWB(ChessColor.WHITE, MoveStrategy.S),
    LWC(ChessColor.WHITE, MoveStrategy.L),
    DW(ChessColor.WHITE, MoveStrategy.D),
    KW(ChessColor.WHITE, MoveStrategy.K),
    LWF(ChessColor.WHITE, MoveStrategy.L),
    SWG(ChessColor.WHITE, MoveStrategy.S),
    TWH(ChessColor.WHITE, MoveStrategy.T),
    BW(ChessColor.WHITE, MoveStrategy.BW),
    EMPTY(ChessColor.EMPTY, MoveStrategy.EMPTY);

    private final ChessColor color;
    private final MoveStrategy moveStrategy;
    private final char term;
    private FigureLabel figureLabel;
    private int index;

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

    public FigureLabel calcFigureLabel(int i) {
        this.figureLabel = new FigureLabel(String.valueOf(getTerm()), this.getColor());
        figureLabel.setFigure(this);
        figureLabel.setIndex(i);
        setIndex(i);
        return figureLabel;
    }

    public FigureLabel getFigureLabel() {
        return figureLabel;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Position calcPosition() {
        final int i = this.index / 8;
        final int j = this.index % 8;
        return new Position(j, i);
    }

    public List<Position> possibleFields(Figure[][] board) {
        return getMoveStrategy().getVerifyMode().possibleFields(this, calcPosition(), board);
    }
}
