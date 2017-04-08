package chess.tools.model;

import chess.tools.game.ChessColor;
import chess.tools.game.EatTuple;
import chess.tools.game.Figure;
import chess.tools.move.MoveTuple;
import chess.tools.move.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * This class represents the main data model of chess application.
 * 
 * @author Erik Rohkohl @date 04.03.2017
 */
public class ChessModel extends Observable{

    private BoardModel model;
    private boolean checkmate = false;
	private boolean stalement = false;
	private List<Boolean> turnValids = new ArrayList<>(); 
    private List<Boolean> chessStates = new ArrayList<>();
    private List<EatTuple> eatTuples = new ArrayList<>();
    private List<MoveTuple> moves = new ArrayList<>();

    public ChessModel() {
        model = new BoardModel();
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
                model.setFigureOnBoard(Figure.EMPTY, i, j);
            }
        }
    }

    private void initFigure(int i, int j, Figure figure) {
        model.setFigureOnBoard(figure, i, j);
        figure.setPosition(new Position(i, j));
    }

    public BoardModel getBoardModel() {
        return model;
    }

    /**
     * delegate from BoardModel
     *
     * @return Figure on Board
     */
    public Figure getFigureOnBoard(int column, int row) {
        return model.getFigureOnBoard(column, row);
    }

    /**
     * delegate from BoardModel
     *
     * @return Figure on Board
     */
    public Figure getFigureOnBoard(Position position) {
        return model.getFigureOnBoard(position);
    }

    /**
     * delegate from BoardModel
     * <p>
     * set value of figure on model
     */
    public void setFigureOnBoard(Figure figure, int column, int row) {
        model.setFigureOnBoard(figure, column, row);
    }

    /**
     * delegate from BoardModel
     * <p>
     * set value of figure on model
     */
    public void setFigureOnBoard(Figure figure, Position position) {
        model.setFigureOnBoard(figure,position);
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
        return "ChessModel [model=\n" + model + "\n, checkmate=" + checkmate + ", stalement=" + stalement
                + "]";
	}

}
