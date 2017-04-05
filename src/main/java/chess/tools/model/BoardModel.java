package chess.tools.model;

import chess.tools.game.Figure;

public class BoardModel {
	private Figure[][] board = new Figure[8][8];
	
	public Figure[][] getFigures(){
		return board;
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
