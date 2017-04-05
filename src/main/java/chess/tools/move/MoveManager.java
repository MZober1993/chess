package chess.tools.move;

import chess.tools.game.EatTuple;
import chess.tools.game.Figure;
import chess.tools.game.GameEndCalculator;
import chess.tools.model.ChessModel;

public class MoveManager {

	private ChessModel chessModel;
    private int turnCounter = 0;
	
	public MoveManager(ChessModel chessModel){
		this.chessModel = chessModel;
	}
	
	public boolean makePossibleMove(Position begin, Position end, Figure oldBegin) {
		Figure oldEnd = chessModel.getBoard()[end.getC()][end.getR()];
		chessModel.getBoard()[end.getC()][end.getR()] = oldBegin;
		chessModel.getBoard()[begin.getC()][begin.getR()] = Figure.EMPTY;
		System.out.println("Move done");

		oldBegin.setPosition(end);
		final boolean chessOrNot = GameEndCalculator.verifyChessState(chessModel.getBoard(), oldBegin.getColor(), true);
		chessModel.getChessStates().add(chessOrNot);
		chessModel.getTurnValids().add(true);
		this.turnCounter++;
		if (!oldEnd.equals(Figure.EMPTY)) {
			chessModel.getEatTuples().add(new EatTuple(oldBegin, oldEnd, turnCounter));
			oldEnd.setEaten(true);
		}
		return chessOrNot;
	}
	
	public int getTurnCounter(){
		return this.turnCounter;
	}
}
