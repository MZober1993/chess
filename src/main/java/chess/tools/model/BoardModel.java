package chess.tools.model;

import chess.tools.game.Figure;
import chess.tools.move.Position;

/**
 * TODO
 * 
 * Vielleicht hast du Recht und man kann diese zusätzliche Klasse wirklich weglassen 
 * sowie das Board als Member in ChessModel lassen. Mir ging es beim Anlegen dieser Klasse nur darum, 
 * die toString()-Methode sinnvoll auszulagern.
 * 
 * @author Review
 *
 */
public class BoardModel {

	/**
	 * TODO
	 * @author Review
	 * 
	 * Die beiden Achten in new Figure[8][8] würde ich durch eine Konstante ersetzen.
	 * 
	 * private final int boradSize = 8;
	 * private Figure[][] board = new Figure[boradSize][boradSize];
	 */
	private Figure[][] board = new Figure[8][8];

    public Figure getFigureOnBoard(Position position) {
        return board[position.getColumn()][position.getRow()];
    }

    public Figure getFigureOnBoard(int column, int row) {
        return board[column][row];
    }

    public void setFigureOnBoard(Figure figure, Position position) {
        board[position.getColumn()][position.getRow()] = figure;
    }

    public void setFigureOnBoard(Figure figure, int column, int row) {
        board[column][row] = figure;
    }

	@Override
	public String toString() {
		String result = "";
        int line = 0;
        final String column = "\ta\tb\tc\td\te\tf\tg\th\n";
        result += column;
        for (int i = 0; i < 8; i++) {
            line = 8 - i;
            result += line;
            for (int j = 0; j < 8; j++) {
            	
            	/**
            	 * TODO 
            	 * FindBug findet es nicht ganz so toll, dass du hier einen String in der Schleife
            	 * konkatenierst. StringBuilder ?
            	 * @author Review
            	 */
                result += "\t" + board[i][j];
            }
            result += "\t" + line + "\n";
        }
        result += column;
        return result;
	}
}
