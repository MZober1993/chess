package chess.tools.game;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

public class ChessTest {

    @Test
    public void ownChessCheckSucceeds() {
        Chess chess = new Chess(Arrays.asList("e2 e3", "e7 e6", "e3 e4", "f8 b4", "d2 d3", "d3 d4"));
        Assert.assertEquals(Arrays.asList(true, true, true, true, false, false), chess.getTurnValids());
    }

    @Test
    public void turnChessCheckSucceeds() {
        Chess chess = new Chess(Arrays.asList("e2 e3", "d7 d6", "f1 b5"));
        Assert.assertEquals(Arrays.asList(true, true, true), chess.getTurnValids());
        Assert.assertEquals(Arrays.asList(false, false, true), chess.getChessStates());
    }

    @Test
    public void turnAndOwnChessCheckSucceeds() {
        Chess chess = new Chess(Arrays.asList("e2 e3", "d7 d6", "f1 b5", "e8 d7"));
        Assert.assertEquals(Arrays.asList(true, true, true, false), chess.getTurnValids());
        Assert.assertEquals(Arrays.asList(false, false, true), chess.getChessStates());
    }

    @Test
    public void turnSucceeds() {
        Chess chess = new Chess(Collections.singletonList("e2 e3"));
        Assert.assertEquals(Collections.singletonList(true), chess.getTurnValids());
    }
}