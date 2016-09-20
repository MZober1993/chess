package chess.tools;

public enum Figure {

    BB(Color.BLACK, new MoveStrategy()),
    TBA(Color.BLACK, new MoveStrategy()),
    SBB(Color.BLACK, new MoveStrategy()),
    LBC(Color.BLACK, new MoveStrategy()),
    DB(Color.BLACK, new MoveStrategy()),
    KB(Color.BLACK, new MoveStrategy()),
    LBF(Color.BLACK, new MoveStrategy()),
    SBG(Color.BLACK, new MoveStrategy()),
    TBH(Color.BLACK, new MoveStrategy()),
    BW(Color.WHITE, new MoveStrategy()),
    TWA(Color.WHITE, new MoveStrategy()),
    SWB(Color.WHITE, new MoveStrategy()),
    LWC(Color.WHITE, new MoveStrategy()),
    DW(Color.WHITE, new MoveStrategy()),
    KW(Color.WHITE, new MoveStrategy()),
    LWF(Color.WHITE, new MoveStrategy()),
    SWG(Color.WHITE, new MoveStrategy()),
    TWH(Color.WHITE, new MoveStrategy()),
    EMPTY(Color.EMPTY, new MoveStrategy());

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

    @Override
    public String toString() {
        return String.valueOf(term);
    }
}
