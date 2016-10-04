package chess.tools.game;

import org.junit.Test;

import java.util.Arrays;

public class ChessTest {
    @Test
    public void chessSucceeds() {
        Chess chess = new Chess(Arrays.asList("e2 e3", "e7 e6", "e3 e4", "f8 b4", "d2 d3", "d3 d4"));
    }

    @Test
    public void simpleChessSucceeds() {
        Chess chess = new Chess(Arrays.asList("e2 e3"));
    }
}