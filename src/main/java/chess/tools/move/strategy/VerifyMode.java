package chess.tools.move.strategy;

import chess.tools.game.Figure;
import chess.tools.move.Position;

public interface VerifyMode {
    boolean verify(Position begin, Position end, Figure[][] board);
}
