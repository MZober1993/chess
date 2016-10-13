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
    private List<EatTuple> eatTuples = new ArrayList<>();
    private int turnCounter = 0;

    public static final PositionMapping MAPPING = new PositionMapping();
    private CheckMateCalculator calculator;

    public Chess(boolean gameModeOn) {
        initialize();

        System.out.println(boardToString(board));
        if (gameModeOn) {
            do {
                if (turn()) {
                    calculateCheckMate();
                    printChessMateState();
                }
                System.out.println(boardToString(board));
            } while (!isCheckMate());
        }
    }

    private void printChessMateState() {
        if (checkmate) {
            System.out.println("--------- check-mate ----------");
        } else {
            System.out.println("no check-mate, move: " + calculator.getLastNoCheckMateFigure() + ": " + calculator.getLastNoCheckMateMove() + " possible!");
        }
    }

    public Chess(List<String> terms) {
        initialize();
        terms.forEach(term -> {
            System.out.println(boardToString(board));
            if (turn(term)) {
                calculateCheckMate();
                printChessMateState();
            }
        });
        System.out.println(boardToString(board));
    }

    private void calculateCheckMate() {
        final ChessColor defenderColor = colorOnTurn();
        final List<Position> forbiddenFields = defenderColor.oppositColor().getAllFromColor().stream()
                .flatMap(figure -> figure.possibleFields(board).stream()).collect(Collectors.toList());
        System.out.println("forbiddenFields = " + forbiddenFields.stream().map(pos -> MAPPING.getCommandMapping().get(pos)).collect(Collectors.toList()));
        System.out.println(boardToString(board));
        calculator = new CheckMateCalculator(defenderColor, forbiddenFields, board);
        checkmate = calculator.validate();
    }


    public Figure[][] initialize() {
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
                board[i][j] = Figure.EMPTY;
            }
        }
        return board;
    }

    private void initFigure(int i, int j, Figure figure) {
        board[i][j] = figure;
        figure.setPosition(new Position(i, j));
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
        return validateColorDoMove(move);
    }

    private boolean turn(String term) {
        MoveTuple move = parser.parse(term);
        if (!move.isPossible()) {
            turnValids.add(false);
            System.out.println("Please choose an other move, this one is not possible.");
        } else {
            return validateColorDoMove(move);
        }
        return false;
    }

    private boolean validateColorDoMove(MoveTuple move) {
        if (move.rightColor(board, turnCounter)) {
            System.out.println(move.getBegin());
            System.out.println("Move:" + MAPPING.getCommandMapping().get(move.getBegin()) + ","
                    + MAPPING.getCommandMapping().get(move.getEnd()));
            return doMove(move);
        } else {
            System.out.println("Not your turn!");
            return false;
        }
    }

    private boolean doMove(MoveTuple move) {
        System.out.println(move);
        final Position begin = move.getBegin();
        final Position end = move.getEnd();
        Figure oldBegin = board[begin.getC()][begin.getR()];
        if (oldBegin.verifyMove(begin, end, board)) {
            return makePossibleMove(begin, end, oldBegin);
        } else {
            System.out.println("Please try again!");
            turnValids.add(false);
        }
        return false;
    }

    private boolean makePossibleMove(Position begin, Position end, Figure oldBegin) {
        Figure oldEnd = board[end.getC()][end.getR()];
        board[end.getC()][end.getR()] = oldBegin;
        board[begin.getC()][begin.getR()] = Figure.EMPTY;
        System.out.println("Move done");

        oldBegin.setPosition(end);
        final boolean chessOrNot = CheckMateCalculator.verifyChessState(board, oldBegin.getColor(), true);
        chessStates.add(chessOrNot);
        turnValids.add(true);
        this.turnCounter++;
        if (!oldEnd.equals(Figure.EMPTY)) {
            eatTuples.add(new EatTuple(oldBegin, oldEnd, turnCounter));
            oldEnd.setEaten(true);
        }
        return chessOrNot;
    }

    public boolean isCheckMate() {
        return checkmate;
    }

    public static String boardToString(Figure[][] board) {
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

    public static void printBoard(Figure[][] board) {
        System.out.println(boardToString(board));
    }

    public ChessColor colorOnTurn() {
        final int i = turnCounter % 2;
        return i == 0 ? ChessColor.WHITE : i == 1
                ? ChessColor.BLACK : ChessColor.EMPTY;
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

    public List<EatTuple> getEatTuples() {
        return eatTuples;
    }

    public CheckMateCalculator getLastCheckMateCalculator() {
        return this.calculator;
    }
}
