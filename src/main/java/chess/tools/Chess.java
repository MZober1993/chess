package chess.tools;

import chess.tools.game.*;
import chess.tools.model.ChessModel;
import chess.tools.move.MoveManager;
import chess.tools.move.MoveTuple;
import chess.tools.move.Position;

import java.util.List;
import java.util.logging.Logger;

/**
 * TODO 
 * Vielleicht sollten wir Chess als eigenes API (ausschließlich Interfaces) in einem seperaten JAR ausliefern. 
 * Damit wir unsere eigentliche Logik besser von der GUI trennen und später die KI besser einbauen können, oder?
 *
 * @author Review
 *
 */
public class Chess {

    private ChessModel chessModel;
    private MoveManager moveManager;
    private Logger logger = Logger.getLogger(Chess.class.getName());
    private CommandParser parser = new CommandParser();
    private final GameEndCalculator endCalculator = new GameEndCalculator();

    public static final PositionMapping MAPPING = new PositionMapping();

    public Chess() {
        chessModel = new ChessModel();
        moveManager = new MoveManager(chessModel);
    }

    public Chess(List<String> terms) {
        chessModel = new ChessModel();
        moveManager = new MoveManager(chessModel);
        terms.forEach(this::processTerm);
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

    public boolean colorValidation(Position begin) {
        Figure oldBegin = chessModel.getFigureOnBoard(begin);
        ChessColor color = oldBegin.getColor();
        return color.validateTurn(moveManager.getTurnCounter());
    }

    public void processTerm(String term) {
        logger.info("Do move: " + term);
        endCalculatorChecks();
        if (turn(term) && !isStalement()) {
            calculateCheckMate();
        }
    }

    public void processMove(MoveTuple move) {
        logger.info("Do move: " + move);
        endCalculatorChecks();
        if (turn(move) && !isStalement()) {
            calculateCheckMate();
        }
    }

    private void endCalculatorChecks() {
        if (moveManager.getTurnCounter() > 10) {
            logger.info("Calculate game end...");
            endCalculator.changeState(colorOnTurn(), chessModel.getBoardModel());
            if (endCalculator.validateStalement()) {
                chessModel.setStalement(true);
            }
        }
    }

    private void calculateCheckMate() {
        logger.info("Calculate check mate...");
        final ChessColor defenderColor = colorOnTurn();
        endCalculator.changeState(defenderColor, chessModel.getBoardModel());
        chessModel.setCheckmate(endCalculator.validateCheckMate());
    }

    private boolean turn(String term) {
        MoveTuple move = parser.parse(term);
        return turn(move);
    }

    private boolean turn(MoveTuple move) {
        chessModel.getMoves().add(move);
        if (!move.isPossible()) {
            chessModel.getTurnValids().add(false);
            logger.warning("Please choose an other move, this one is not possible.");
        } else {
            return validateColorDoMove(move);
        }
        return false;
    }

    private boolean validateColorDoMove(MoveTuple move) {
        if (move.rightColor(chessModel.getBoardModel(), moveManager.getTurnCounter())) {
            logger.info(move.getBegin().toString());
            logger.info("Move:" + MAPPING.getCommandMapping().get(move.getBegin()) + ","
                    + MAPPING.getCommandMapping().get(move.getEnd()));
            return doMove(move);
        } else {
            logger.warning("Not your turn!");
            return false;
        }
    }

    private boolean doMove(MoveTuple move) {
        logger.info(move.toString());
        final Position begin = move.getBegin();
        final Position end = move.getEnd();
        Figure oldBegin = chessModel.getFigureOnBoard(begin);
        if (oldBegin.verifyMove(begin, end, chessModel.getBoardModel())) {
            return moveManager.makePossibleMove(begin, end, oldBegin);
        } else {
            logger.warning("Please try again!");
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

    public GameEndCalculator getEndCalculator() {
        return this.endCalculator;
    }

    public boolean isStalement() {
        if (chessModel.isStalement()) {
            logger.warning("--------------- Patt ----------------");
        }
        return chessModel.isStalement();
    }

    public ChessModel getChessModel() {
        return chessModel;
    }
}
