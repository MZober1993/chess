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
                if (turn()) {
                    calculateCheckMate();
                }
                System.out.println(boardToString());
            } while (!isCheckMate());
        }
    }

    public Chess(List<String> terms) {
        initialize();
        terms.forEach(term -> {
            System.out.println(boardToString());
            if (turn(term)) {
                calculateCheckMate();
            }
        });
        System.out.println(boardToString());
    }

    private void calculateCheckMate() {

    }


    private boolean verifyChessState(Figure[][] board, ChessColor color) {
        for (Figure figure : color.allFromColor()) {
            final List<Position> positions = figure.getMoveStrategy().getVerifyMode()
                    .possibleFields(figure, board);
            final List<Figure> figures = positions
                    .stream().map(pos -> Figure.figureForPos(board, pos)).collect(Collectors.toList());
            System.out.println(figure.getColor() + " " + figure + " found figures = " + figures);
            System.out.println();
            if (figures.stream().anyMatch(f -> f.equals(color.oppositColor().king()))) {
                System.out.println("Chess for color: " + color.oppositColor());
                return true;
            }
        }
        return false;
    }

    public Figure[][] initialize() {
        final List<Figure> blackCoreLine = ChessColor.BLACK.allWithoutPawn();
        final List<Figure> blackPawnLine = ChessColor.BLACK.allPawns();
        final List<Figure> whiteCoreLine = ChessColor.WHITE.allWithoutPawn();
        final List<Figure> whitePawnLine = ChessColor.WHITE.allPawns();
        for (int j = 0; j < 8; j++) {
            initFigure(0, j, blackCoreLine.get(j));
            initFigure(1, j, blackPawnLine.get(j));
            initFigure(6, j, whitePawnLine.get(j));
            initFigure(7, j, whiteCoreLine.get(j));
            for (int i = 2; i < 6; i++) {
                board[i][j] = Figure.EMPTY;
            }
        }
        return board;
    }

    private void initFigure(int i, int j, Figure figure) {
        board[i][j] = figure;
        figure.setPosition(new Position(i, j));
    }

    private boolean turn(String term) {
        MoveTuple move = parser.parse(term);
        if (!move.isPossible()) {
            turnValids.add(false);
            System.out.println("Please choose an other move, this one is not possible.");
        } else {
            System.out.println(move.getBegin());
            System.out.println("Move:" + MAPPING.getCommandMapping().get(move.getBegin()) + ","
                    + MAPPING.getCommandMapping().get(move.getEnd()));
            return doMove(move);
        }
        return false;
    }

    private boolean doMove(MoveTuple move) {
        System.out.println(move);
        final Position begin = move.getBegin();
        final Position end = move.getEnd();
        Figure oldBegin = board[begin.getC()][begin.getR()];
        if (oldBegin.verifyMove(begin, end, board)) {
            Figure oldEnd = board[end.getC()][end.getR()];
            board[end.getC()][end.getR()] = oldBegin;
            board[begin.getC()][begin.getR()] = Figure.EMPTY;
            System.out.println("Move done");

            oldBegin.setPosition(end);
            final boolean chessOrNot = verifyChessState(board, oldBegin.getColor());
            chessStates.add(chessOrNot);
            turnValids.add(true);
            return chessOrNot;
        } else {
            System.out.println("Please try again!");
            turnValids.add(false);
        }
        return false;
    }

    public boolean turn() {
        String command = console.readLine(INFO_ABOUT_TURN);
        MoveTuple move = parser.parse(command);
        while (!move.isPossible()) {
            System.out.println("Please choose an other move, this one is not possible.");
            command = console.readLine(INFO_ABOUT_TURN);
            move = parser.parse(command);
            turnValids.add(false);
        }
        return doMove(move);
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
