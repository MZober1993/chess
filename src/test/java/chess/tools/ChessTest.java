package chess.tools;

import chess.tools.Chess;
import chess.tools.game.EatTuple;
import chess.tools.game.Figure;
import chess.tools.move.MoveTuple;
import chess.tools.move.Position;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChessTest {

    @Test
    public void ownChessCheckSucceeds() {
        Chess chess = new Chess(Arrays.asList("e2 e3", "e7 e6", "e3 e4", "f8 b4", "d2 d3"));
        Assert.assertEquals(Arrays.asList(true, true, true, true, false), chess.getChessModel().getTurnValids());
    }

    @Test
    public void turnChessCheckSucceeds() {
        Chess chess = new Chess(Arrays.asList("e2 e3", "d7 d6", "f1 b5"));
        Assert.assertEquals(Arrays.asList(true, true, true), chess.getChessModel().getTurnValids());
        Assert.assertEquals(Arrays.asList(false, false, true), chess.getChessModel().getChessStates());
    }

    @Test
    public void eatSucceeds() {
        Chess chess = new Chess(Arrays.asList("e2 e3", "e7 e6", "f1 b5", "a7 a6", "b5 d7"));
        Assert.assertEquals(Arrays.asList(true, true, true, true, true), chess.getChessModel().getTurnValids());
        Assert.assertEquals(Arrays.asList(false, false, false, false, true), chess.getChessModel().getChessStates());

        final List<EatTuple> eatTuples = chess.getChessModel().getEatTuples();
        Assert.assertEquals(1, eatTuples.size());
        final EatTuple eatTuple = eatTuples.get(0);

        Assert.assertEquals('L', eatTuple.getAttacker().getTerm());
        Assert.assertEquals('B', eatTuple.getLoser().getTerm());
        Assert.assertEquals(5, eatTuple.getTurnCount());
    }

    @Test
    public void turnAndOwnChessCheckSucceeds() {
        Chess chess = new Chess(Arrays.asList("e2 e3", "d7 d6", "f1 b5", "e8 d7"));
        Assert.assertEquals(Arrays.asList(true, true, true, false), chess.getChessModel().getTurnValids());
        Assert.assertEquals(Arrays.asList(false, false, true), chess.getChessModel().getChessStates());
    }

    @Test
    public void turnSucceeds() {
        Chess chess = new Chess(Collections.singletonList("e2 e3"));
        Assert.assertEquals(Collections.singletonList(true), chess.getChessModel().getTurnValids());
    }

    @Test
    public void noCheckMateBug() {
        Chess chess = new Chess(Arrays.asList("e2 e3", "c7 c6", "f1 c4"));
        Assert.assertEquals(Arrays.asList(true, true, true), chess.getChessModel().getTurnValids());
    }

    @Test
    public void noChessMateSucceeds() {
        Chess chess = new Chess(Arrays.asList("e2 e3", "d7 d6", "d1 h5", "b8 c6", "f1 c4", "a7 a6", "h5 f7"));
        Assert.assertEquals(Stream.of(new EatTuple(Figure.DW, Figure.BBF, 7)).collect(Collectors.toList())
                , chess.getChessModel().getEatTuples());
        Assert.assertFalse(chess.isCheckMate());
        final MoveTuple lastNoCheckMateMove = chess.getEndCalculator().getLastNoCheckMateMove();
        Assert.assertEquals(new MoveTuple(new Position(0, 4), new Position(1, 3), true), lastNoCheckMateMove);
    }

    @Test
    public void chessMateSucceeds() {
        Chess chess = new Chess(Arrays.asList("e2 e3", "b7 b6", "d1 h5", "b8 c6", "f1 c4", "a7 a6", "h5 f7"));
        Assert.assertEquals(Stream.of(new EatTuple(Figure.DW, Figure.BBF, 7)).collect(Collectors.toList())
                , chess.getChessModel().getEatTuples());
        Assert.assertTrue(chess.isCheckMate());
    }
}