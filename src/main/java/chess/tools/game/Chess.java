package chess.tools.game;

import chess.tools.move.MoveTuple;
import chess.tools.move.Position;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

public class Chess {

    private static final String INFO_ABOUT_TURN = "Please type your move in form of: 'e4 e5'";
    private Figure[][] board = new Figure[8][8];
    private Console console = System.console();
    private CommandParser parser = new CommandParser();
    private boolean checkmate = false;
    private boolean stalement = false;
    private List<Boolean> turnValids = new ArrayList<>();
    private List<Boolean> chessStates = new ArrayList<>();
    private List<EatTuple> eatTuples = new ArrayList<>();
    private List<MoveTuple> moves = new ArrayList<>();
    private final GameEndCalculator endCalculator = new GameEndCalculator();
    private int turnCounter = 0;

    public static final PositionMapping MAPPING = new PositionMapping();

    public Chess(boolean gameModeOn) {
        initialize();

        System.out.println(boardToString(board));
        if (gameModeOn) {
            do {
                if (turnCounter > 10) {
                    endCalculator.changeState(colorOnTurn(), board);
                    if (endCalculator.validateStalement()) {
                        stalement = true;
                    }
                }
                if (turn() && !stalement) {
                    calculateCheckMate();
                    printCheckMateState();
                }
                System.out.println(boardToString(board));
            } while (!isCheckMate() && !isStalement());
        }
    }

    /**
     * @param move - a possible move
     * @return valid or not
     */
    public boolean gameStep(MoveTuple move) {
        final int sizeBefore = turnValids.size();
        processMove(move);
        printBoard();
        return (turnValids.size() == sizeBefore + 1) && turnValids.get(turnValids.size() - 1);
    }

    public Figure figureOnBoard(Position position) {
        return Figure.figureForPos(board, position);
    }

    public boolean colorValidation(Position begin) {
        Figure oldBegin = board[begin.getC()][begin.getR()];
        return oldBegin.getColor().validateTurn(turnCounter);
    }

    private void printCheckMateState() {
        if (checkmate) {
            System.out.println("--------- check-mate ----------");
        } else {
            System.out.println("no check-mate, move: " + endCalculator.getLastNoCheckMateMove().getFigure() + ": " + endCalculator.getLastNoCheckMateMove() + " possible!");
        }
    }

    public Chess(List<String> terms) {
        initialize();
        terms.forEach(this::processTerm);
        System.out.println(boardToString(board));
    }

    public void processTerm(String term) {
        endCalculatorChecks();
        if (turn(term) && !isStalement()) {
            calculateCheckMate();
            printCheckMateState();
        }
    }

    public void processMove(MoveTuple move) {
        endCalculatorChecks();
        if (turn(move) && !isStalement()) {
            calculateCheckMate();
            printCheckMateState();
        }
    }

    private void endCalculatorChecks() {
        System.out.println(boardToString(board));
        if (turnCounter > 10) {
            endCalculator.changeState(colorOnTurn(), board);
            if (endCalculator.validateStalement()) {
                stalement = true;
            }
        }
    }

    private void calculateCheckMate() {
        final ChessColor defenderColor = colorOnTurn();
        System.out.println(boardToString(board));
        endCalculator.changeState(defenderColor, board);
        checkmate = endCalculator.validateCheckMate();
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
        return turn(move);
    }

    private boolean turn(MoveTuple move) {
        moves.add(move);
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
        final boolean chessOrNot = GameEndCalculator.verifyChessState(board, oldBegin.getColor(), true);
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

    public void printBoard() {
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

    public List<MoveTuple> getMoves() {
        return moves;
    }

    public GameEndCalculator getEndCalculator() {
        return this.endCalculator;
    }

    public boolean isStalement() {
        if (stalement) {
            System.out.println("--------------- Patt ----------------");
        }
        return stalement;
    }
}
