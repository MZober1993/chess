package chess.tools.move.strategy;

import chess.tools.model.BoardModel;
import chess.tools.move.Direction;
import chess.tools.move.Position;

import java.util.Collections;

public enum MoveStrategy {

    BB(new PawnMode(Direction.PAWN_BLACK)),
    BW(new PawnMode(Direction.PAWN_WHITE)),
    K(new DefaultMode(false, Direction.QUEEN)),
    S(new DefaultMode(false, Direction.SPRINGER)),
    D(new DefaultMode(true, Direction.QUEEN)),
    T(new DefaultMode(true, Direction.TOWER)),
    L(new DefaultMode(true, Direction.BISHOP)),
    EMPTY(new DefaultMode(true, Collections.emptyList()));

    private VerifyMode verifyMode;

    MoveStrategy(VerifyMode verifyMode) {
        this.verifyMode = verifyMode;
    }

    public boolean verify(Position begin, Position end, BoardModel model) {
        return verifyMode.verify(begin, end, model);
    }

    public VerifyMode getVerifyMode() {
        return verifyMode;
    }
}
