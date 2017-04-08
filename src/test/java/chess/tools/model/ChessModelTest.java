package chess.tools.model;

import chess.tools.game.ChessColor;
import chess.tools.game.Figure;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ChessModelTest {
	
	private ChessModel chessModel;
	
	@Before
	public void buildTestEnvironemt(){
		chessModel = new ChessModel();
	}
	
	@Test
	public void chessModelShouldBeCorrectInit(){
		final List<Figure> blackCoreLine = ChessColor.BLACK.getAllWithoutPawn();
		final List<Figure> blackPawnLine = ChessColor.BLACK.getAllPawns();
		final List<Figure> whiteCoreLine = ChessColor.WHITE.getAllWithoutPawn();
		final List<Figure> whitePawnLine = ChessColor.WHITE.getAllPawns();
		
		for (int j = 0; j < 8; j++) {
			assertEquals(chessModel.getFigureOnBoard(0, j), blackCoreLine.get(j));
			assertEquals(chessModel.getFigureOnBoard(1, j), blackPawnLine.get(j));
			assertEquals(chessModel.getFigureOnBoard(6, j), whitePawnLine.get(j));
			assertEquals(chessModel.getFigureOnBoard(7, j), whiteCoreLine.get(j));
			for (int i = 2; i < 6; i++) {
				assertEquals(chessModel.getFigureOnBoard(i, j), Figure.EMPTY);
			}
		}
	}
	
	@Test
	public void testCheckmate(){
		boolean returnedValue = chessModel.isCheckmate();
		assertEquals(false, returnedValue);
	}
	
	@Test
	public void testStalement(){
		boolean returnedValue = chessModel.isStalement();
		assertEquals(false, returnedValue);
	}
}
