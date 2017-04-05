package chess.tools.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;

import chess.tools.game.ChessColor;
import chess.tools.game.EatTuple;
import chess.tools.game.Figure;
import chess.tools.move.MoveTuple;
import chess.tools.move.Position;

/**
 * This class represents the main data model of chess application.
 * 
 * @author Erik Rohkohl @date 04.03.2017
 */
public class ChessModel extends Observable{
	
	private BoardModel board;
	private boolean checkmate = false;
	private boolean stalement = false;
	private List<Boolean> turnValids = new ArrayList<>(); 
    private List<Boolean> chessStates = new ArrayList<>();
    private List<EatTuple> eatTuples = new ArrayList<>();
    private List<MoveTuple> moves = new ArrayList<>();

	public ChessModel(boolean checkmate, boolean stalement) {
		board = new BoardModel();
		this.checkmate = checkmate;
		this.stalement = stalement;
		this.initialize();
	}

	public void initialize() {
		ChessColor.BLACK.initFigureLists();
		ChessColor.WHITE.initFigureLists();
		ChessColor.EMPTY.initFigureLists();
		final List<Figure> blackCoreLine = ChessColor.BLACK.getAllWithoutPawn();
		final List<Figure> blackPawnLine = ChessColor.BLACK.getAllPawns();
		final List<Figure> whiteCoreLine = ChessColor.WHITE.getAllWithoutPawn();
		final List<Figure> whitePawnLine = ChessColor.WHITE.getAllPawns();
		for (int j = 0; j < 8; j++) {
			initFigure(0, j, blackCoreLine.get(j));
			initFigure(1, j, blackPawnLine.get(j));
			initFigure(6, j, whitePawnLine.get(j));
			initFigure(7, j, whiteCoreLine.get(j));
			
			for (int i = 2; i < 6; i++) {
				board.getFigures()[i][j] = Figure.EMPTY;
			}
		}
	}
	
	private void initFigure(int i, int j, Figure figure) {
		board.getFigures()[i][j] = figure;
		figure.setPosition(new Position(i, j));
	}

	public Figure[][] getBoard() {
		return board.getFigures();
	}

	public boolean isCheckmate() {
		return checkmate;
	}

	public void setCheckmate(boolean checkmate) {
		this.checkmate = checkmate;
	}

	public boolean isStalement() {
		return stalement;
	}

	public void setStalement(boolean stalement) {
		this.stalement = stalement;
	}

	public List<Boolean> getTurnValids() {
        return turnValids;
    }

    public List<Boolean> getChessStates() {
        return chessStates;
    }

    public List<EatTuple> getEatTuples() {
        return eatTuples;
    }

    public List<MoveTuple> getMoves() {
        return moves;
    }
    
	@Override
	public String toString() {
		return "ChessModel [board=" + Arrays.toString(board.getFigures()) + ", checkmate=" + checkmate + ", stalement=" + stalement
				+ "]";
	}

}
