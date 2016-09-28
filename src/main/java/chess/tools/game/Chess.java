package chess.tools.game;

import chess.tools.move.MoveTuple;
import chess.tools.move.Position;

import java.io.Console;
import java.util.List;

public class Chess {

    private static final String INFO_ABOUT_TURN = "Please type your move in form of: 'e4 e5'";
    private Figure[][] board = new Figure[8][8];
    private Console console = System.console();
    private CommandParser parser = new CommandParser();
    private boolean checkmate = false;

    public Chess() {
        initialize();

        System.out.println(boardToString());
        do {
            turn();
            System.out.println(boardToString());
            calculateCheckMate();
        } while (!isCheckMate());
    }

    private void calculateCheckMate() {

    }

    private void initialize() {
        final List<Figure> blackCoreLine = Color.BLACK.allWithoutPawn();
        final List<Figure> whiteCoreLine = Color.WHITE.allWithoutPawn();
        for (int j = 0; j < 8; j++) {
            board[0][j] = blackCoreLine.get(j);
            board[1][j] = Figure.BB;
            board[6][j] = Figure.BW;
            board[7][j] = whiteCoreLine.get(j);
            for (int i = 2; i < 6; i++) {
                board[i][j] = Figure.EMPTY;
            }
        }
    }

    public void turn() {
        String command = console.readLine(INFO_ABOUT_TURN);
        MoveTuple move = parser.parse(command);
        while (!move.isPossible()) {
            System.out.println("Please choose an other move, this one is not possible.");
            command = console.readLine(INFO_ABOUT_TURN);
            move = parser.parse(command);
        }
        System.out.println(move);
        final Position begin = move.getBegin();
        final Position end = move.getEnd();
        //TODO: check if MoveStrategy matches the given end Position
        Figure oldBegin = board[begin.getC()][begin.getR()];
        if (oldBegin.verifyMove(begin, end, board)) {
            Figure oldEnd = board[end.getC()][end.getR()];
            board[end.getC()][end.getR()] = oldBegin;
            board[begin.getC()][begin.getR()] = Figure.EMPTY;
            //TODO: move does not change
            System.out.println("Move done");
        } else {
            System.out.println("Please try again!");
        }

    }

    public boolean isCheckMate() {
        return checkmate;
    }

    public String boardToString() {
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
