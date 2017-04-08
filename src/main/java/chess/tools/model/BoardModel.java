package chess.tools.model;

import chess.tools.game.Figure;
import chess.tools.move.Position;

public class BoardModel {

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
                result += "\t" + board[i][j];
            }
            result += "\t" + line + "\n";
        }
        result += column;
        return result;
	}
}
