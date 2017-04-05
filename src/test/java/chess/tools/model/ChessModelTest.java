package chess.tools.model;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import chess.tools.game.ChessColor;
import chess.tools.game.Figure;

public class ChessModelTest {
	
	private ChessModel chessModel;
	
	@Before
	public void buildTestEnvironemt(){
		chessModel = new ChessModel(false, false);
	}
	
	@Test
	public void chessModelShouldBeCorrectInit(){
		final List<Figure> blackCoreLine = ChessColor.BLACK.getAllWithoutPawn();
		final List<Figure> blackPawnLine = ChessColor.BLACK.getAllPawns();
		final List<Figure> whiteCoreLine = ChessColor.WHITE.getAllWithoutPawn();
		final List<Figure> whitePawnLine = ChessColor.WHITE.getAllPawns();
		
		for (int j = 0; j < 8; j++) {
			assertEquals(chessModel.getBoard()[0][j], blackCoreLine.get(j));
			assertEquals(chessModel.getBoard()[1][j], blackPawnLine.get(j));
			assertEquals(chessModel.getBoard()[6][j], whitePawnLine.get(j));
			assertEquals(chessModel.getBoard()[7][j], whiteCoreLine.get(j));
			for (int i = 2; i < 6; i++) {
				assertEquals(chessModel.getBoard()[i][j], Figure.EMPTY);
			}
		}
	}
	
	@Test
	public void testCheckmate(){
		boolean expectedValue = false;
		boolean returnedValue = chessModel.isCheckmate();
		assertEquals(expectedValue, returnedValue);
	}
	
	@Test
	public void testStalement(){
		boolean expectedValue = false;
		boolean returnedValue = chessModel.isStalement();
		assertEquals(expectedValue, returnedValue);
	}
}
