package chess.tools.game;

import chess.tools.move.MoveTuple;
import chess.tools.move.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CheckMateCalculator {
    private final ChessColor defenderColor;
    private final List<Position> forbiddenFields;
    private final Figure[][] board;
    private final Figure king;
    private Figure oldFigureBeforeSimulation;
    private MoveTuple lastNoCheckMateMove = new MoveTuple(new Position(0, 0), new Position(0, 0), false);
    private Figure lastNoCheckMateFigure = Figure.EMPTY;
    private List<Position> possibleKingMoves = new ArrayList<>();

    public CheckMateCalculator(ChessColor defenderColor, List<Position> forbiddenFields, Figure[][] board) {
        this.defenderColor = defenderColor;
        this.king = defenderColor.king();
        this.forbiddenFields = forbiddenFields;
        this.board = board;
    }

    /**
     * Validate the chess-mate state
     *
     * @return checkmate
     */
    public boolean validate() {
        if (kingMoveWithoutAttackingPossible()) {
            setLastNoCheckMate(king, king.getPosition(), possibleKingMoves.get(0));
            return false;
        } else {
            final Map<Figure, List<Position>> moves = MoveCalculator.calcMoves(defenderColor.calcNotEaten(), board);
            for (Map.Entry<Figure, List<Position>> entry : moves.entrySet()) {
                for (Position pos : entry.getValue()) {
                    final Figure key = entry.getKey();
                    final Position oldPosition = key.getPosition();
                    System.out.println("simulate for: " + key.getColor() + " " + key + " on " + Chess.MAPPING.getCommandMapping().get(pos));
                    simulateMove(key, pos);
                    if (!verifyChessState(board, defenderColor.oppositColor(), false)) {
                        undoSimulation(key, pos);
                        setLastNoCheckMate(key, oldPosition, pos);
                        return false;
                    }
                    undoSimulation(key, pos);
                }
            }
        }
        return true;
    }

    private void setLastNoCheckMate(Figure figure, Position begin, Position end) {
        this.lastNoCheckMateMove = new MoveTuple(begin, end, true);
        this.lastNoCheckMateFigure = figure;
    }

    private void simulateMove(Figure figure, Position position) {
        final Position figurePosition = figure.getPosition();
        board[figurePosition.getC()][figurePosition.getR()] = Figure.EMPTY;
        oldFigureBeforeSimulation = board[position.getC()][position.getR()];
        board[position.getC()][position.getR()] = figure;
    }

    private void undoSimulation(Figure figure, Position position) {
        final Position figurePosition = figure.getPosition();
        board[figurePosition.getC()][figurePosition.getR()] = figure;
        board[position.getC()][position.getR()] = oldFigureBeforeSimulation;
    }

    private boolean kingMoveWithoutAttackingPossible() {
        final List<Position> positions = king.possibleFields(board);
        if (positions.isEmpty() || positions.stream().allMatch(forbiddenFields::contains)) {
            return false;
        }

        possibleKingMoves = positions.stream().filter(pos -> !forbiddenFields.contains(pos))
                .filter(pos -> Figure.figureForPos(board, pos).equals(Figure.EMPTY)).collect(Collectors.toList());
        return !possibleKingMoves.isEmpty();
    }

    /**
     * @param board           - board
     * @param color           - color of attacker
     * @param loggingMessages - logging messages on?
     * @return chess?
     */
    public static boolean verifyChessState(Figure[][] board, ChessColor color, boolean loggingMessages) {
        for (Figure figure : color.getAllFromColor()) {
            final List<Position> positions = figure.getMoveStrategy().getVerifyMode()
                    .possibleFields(figure, board);
            final List<Figure> figures = positions
                    .stream().map(pos -> Figure.figureForPos(board, pos)).collect(Collectors.toList());
            final List<Figure> chessSources = figures.stream().filter(f -> f.equals(color.oppositColor().king()))
                    .collect(Collectors.toList());
            if (!chessSources.isEmpty()) {
                if (loggingMessages) {
                    System.out.println("Chess for color: " + color.oppositColor() + " from: " + figure);
                }
                return true;
            }
        }
        return false;
    }

    public ChessColor getDefenderColor() {
        return defenderColor;
    }

    public List<Position> getForbiddenFields() {
        return forbiddenFields;
    }

    public Figure[][] getBoard() {
        return board;
    }

    public MoveTuple getLastNoCheckMateMove() {
        return lastNoCheckMateMove;
    }

    public Figure getLastNoCheckMateFigure() {
        return lastNoCheckMateFigure;
    }
}
