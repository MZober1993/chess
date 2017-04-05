package chess.tools;

import java.io.Console;
import java.util.List;

import chess.tools.game.ChessColor;
import chess.tools.game.CommandParser;
import chess.tools.game.Figure;
import chess.tools.game.GameEndCalculator;
import chess.tools.game.PositionMapping;
import chess.tools.model.ChessModel;
import chess.tools.move.MoveManager;
import chess.tools.move.MoveTuple;
import chess.tools.move.Position;

public class Chess{

    private static final String INFO_ABOUT_TURN = "Please type your move in form of: 'e4 e5'";
 
    private ChessModel chessModel;
    private MoveManager moveManager;
    
    private Console console = System.console();
    private CommandParser parser = new CommandParser();
    private final GameEndCalculator endCalculator = new GameEndCalculator();

    public static final PositionMapping MAPPING = new PositionMapping();

    public Chess(boolean gameModeOn) {
    	
    	chessModel = new ChessModel(false, false);        
        /*
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
        }*/
    }

    public Chess(List<String> terms) {
    	chessModel = new ChessModel(false, false);
    	moveManager = new MoveManager(chessModel);
        terms.forEach(this::processTerm);
        // TODO add Logging
    }
    
    /**
     * @param move - a possible move
     * @return valid or not
     */
    public boolean gameStep(MoveTuple move) {
        final int sizeBefore = chessModel.getTurnValids().size();
        processMove(move);
        int size = chessModel.getTurnValids().size();
        return (size == sizeBefore + 1) && chessModel.getTurnValids().get(size - 1);
    }

    public Figure figureOnBoard(Position position) { // TODO extract to class chessModel
        return Figure.figureForPos(chessModel.getBoard(), position);
    }

    public boolean colorValidation(Position begin) {
        Figure oldBegin = chessModel.getBoard()[begin.getC()][begin.getR()];
        return oldBegin.getColor().validateTurn(moveManager.getTurnCounter());
    }
  

    public void processTerm(String term) { 
        endCalculatorChecks();
        if (turn(term) && !isStalement()) {
            calculateCheckMate();
        }
    }

    public void processMove(MoveTuple move) {
        endCalculatorChecks();
        if (turn(move) && !isStalement()) {
            calculateCheckMate();
        }
    }

    private void endCalculatorChecks() {
        if (moveManager.getTurnCounter() > 10) {
            endCalculator.changeState(colorOnTurn(), chessModel.getBoard());
            if (endCalculator.validateStalement()) {
                chessModel.setStalement(true);
            }
        }
    }

    private void calculateCheckMate() {
        final ChessColor defenderColor = colorOnTurn();
        endCalculator.changeState(defenderColor, chessModel.getBoard());
        chessModel.setCheckmate(endCalculator.validateCheckMate());
    }

    public boolean turn() {
        String command = console.readLine(INFO_ABOUT_TURN);
        MoveTuple move = parser.parse(command);
        while (!move.isPossible()) {
            System.out.println("Please choose an other move, this one is not possible.");
            command = console.readLine(INFO_ABOUT_TURN);
            move = parser.parse(command);
            chessModel.getTurnValids().add(false);
        }
        return validateColorDoMove(move);
    }

    private boolean turn(String term) {
        MoveTuple move = parser.parse(term);
        return turn(move);
    }

    private boolean turn(MoveTuple move) {
        chessModel.getMoves().add(move);
        if (!move.isPossible()) {
            chessModel.getTurnValids().add(false);
            System.out.println("Please choose an other move, this one is not possible.");
        } else {
            return validateColorDoMove(move);
        }
        return false;
    }

    private boolean validateColorDoMove(MoveTuple move) {
        if (move.rightColor(chessModel.getBoard(), moveManager.getTurnCounter())) {
            System.out.println(move.getBegin());
            System.out.println("Move:" + MAPPING.getCommandMapping().get(move.getBegin()) + ","
                    + MAPPING.getCommandMapping().get(move.getEnd()));
            return doMove(move);
        } else {
            //System.out.println("Not your turn!");
            return false;
        }
    }

    private boolean doMove(MoveTuple move) {
        System.out.println(move);
        final Position begin = move.getBegin();
        final Position end = move.getEnd();
        Figure oldBegin = chessModel.getBoard()[begin.getC()][begin.getR()];
        if (oldBegin.verifyMove(begin, end, chessModel.getBoard())) {
            return moveManager.makePossibleMove(begin, end, oldBegin);
        } else {
            //System.out.println("Please try again!");
            chessModel.getTurnValids().add(false);
        }
        return false;
    }

    public boolean isCheckMate() {
        return chessModel.isCheckmate();
    }

    public ChessColor colorOnTurn() {
        final int i = moveManager.getTurnCounter() % 2;
        return i == 0 ? ChessColor.WHITE : i == 1
                ? ChessColor.BLACK : ChessColor.EMPTY;
    }

    public Figure[][] getBoard() {
        return chessModel.getBoard();
    }

    public GameEndCalculator getEndCalculator() {
        return this.endCalculator;
    }

    public boolean isStalement() {
        //if (chessModel.isStalement()) {
          //  System.out.println("--------------- Patt ----------------");
        //}
        return chessModel.isStalement();
    }
    
    public ChessModel getChessModel(){
    	return chessModel;
    }
}
