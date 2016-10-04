package chess.tools.game;

import chess.tools.move.MoveTuple;
import chess.tools.move.Position;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Chess {

    private static final String INFO_ABOUT_TURN = "Please type your move in form of: 'e4 e5'";
    private Figure[][] board = new Figure[8][8];
    private Console console = System.console();
    private CommandParser parser = new CommandParser();
    private boolean checkmate = false;
    private List<Boolean> turnValids = new ArrayList<>();
    private List<Boolean> chessStates = new ArrayList<>();

    public static final PositionMapping MAPPING = new PositionMapping();

    public Chess(boolean gameModeOn) {
        initialize();

        System.out.println(boardToString());
        if (gameModeOn) {
            do {
                turn();
                System.out.println(boardToString());
                calculateCheckMate();
            } while (!isCheckMate());
        }
    }

    public Chess(List<String> terms) {
        initialize();
        terms.forEach(term -> {
            System.out.println(boardToString());
            turn(term);
            calculateCheckMate();
        });
        System.out.println(boardToString());
    }

    private void calculateCheckMate() {

    }


    private boolean verifyChessState(Figure[][] board, ChessColor color) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Figure figure = board[i][j];
                if (!figure.getColor().equals(ChessColor.EMPTY) && figure.getColor().equals(color)) {
                    final List<Position> positions = figure.getMoveStrategy().getVerifyMode()
                            .possibleFields(figure, new Position(i, j), board);
                    final List<Figure> figures = positions
                            .stream().map(pos -> Figure.figureForPos(board, pos)).collect(Collectors.toList());
                    System.out.println(figure.getColor() + " " + figure + " found figures = " + figures);
                    System.out.println();
                    if (figures.stream().anyMatch(f -> f.equals(color.oppositColor().king()))) {
                        System.out.println("Chess for color: " + color.oppositColor());
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Figure[][] initialize() {
        final List<Figure> blackCoreLine = ChessColor.BLACK.allWithoutPawn();
        final List<Figure> whiteCoreLine = ChessColor.WHITE.allWithoutPawn();
        for (int j = 0; j < 8; j++) {
            board[0][j] = blackCoreLine.get(j);
            board[1][j] = Figure.BB;
            board[6][j] = Figure.BW;
            board[7][j] = whiteCoreLine.get(j);
            for (int i = 2; i < 6; i++) {
                board[i][j] = Figure.EMPTY;
            }
        }
        return board;
    }

    private void turn(String term) {
        MoveTuple move = parser.parse(term);
        if (!move.isPossible()) {
            turnValids.add(false);
            System.out.println("Please choose an other move, this one is not possible.");
        } else {
            System.out.println(move.getBegin());
            System.out.println("Move:" + MAPPING.getCommandMapping().get(move.getBegin()) + ","
                    + MAPPING.getCommandMapping().get(move.getEnd()));
            doMove(move);
        }
    }

    private void doMove(MoveTuple move) {
        System.out.println(move);
        final Position begin = move.getBegin();
        final Position end = move.getEnd();
        Figure oldBegin = board[begin.getC()][begin.getR()];
        if (oldBegin.verifyMove(begin, end, board)) {
            Figure oldEnd = board[end.getC()][end.getR()];
            board[end.getC()][end.getR()] = oldBegin;
            board[begin.getC()][begin.getR()] = Figure.EMPTY;
            System.out.println("Move done");
            turnValids.add(true);
            chessStates.add(verifyChessState(board, oldBegin.getColor()));
        } else {
            System.out.println("Please try again!");
            turnValids.add(false);
        }
    }

    public void turn() {
        String command = console.readLine(INFO_ABOUT_TURN);
        MoveTuple move = parser.parse(command);
        while (!move.isPossible()) {
            System.out.println("Please choose an other move, this one is not possible.");
            command = console.readLine(INFO_ABOUT_TURN);
            move = parser.parse(command);
            turnValids.add(false);
        }
        doMove(move);
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

    public Figure[][] getBoard() {
        return board;
    }

    public List<Boolean> getTurnValids() {
        return turnValids;
    }

    public List<Boolean> getChessStates() {
        return chessStates;
    }
}
