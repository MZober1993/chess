package chess.tools.game;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ChessColor {
    BLACK(Color.darkGray, 1), WHITE(Color.lightGray, 0), EMPTY(Color.gray, 2);

    private Color color;
    private int turnNumber;
    private List<Figure> allPawns = new ArrayList<>();
    private List<Figure> allWithoutPawn = new ArrayList<>();
    private List<Figure> allFromColor = new ArrayList<>();
    private List<Figure> allWithoutKing = new ArrayList<>();

    ChessColor(Color color, int turnNumber) {
        this.color = color;
        this.turnNumber = turnNumber;
    }

    public boolean validateTurn(int turn) {
        final int i = turn % 2;
        return i == turnNumber;
    }

    public List<Figure> getAllPawns() {
        return allPawns;
    }

    public List<Figure> getAllWithoutPawn() {
        return allWithoutPawn;
    }

    public List<Figure> getAllFromColor() {
        return allFromColor;
    }

    public List<Figure> getAllWithoutKing() {
        return allWithoutKing;
    }

    public List<Figure> calcNotEaten() {
        return getAllFromColor().stream().filter(figure -> !figure.isEaten()).collect(Collectors.toList());
    }

    public void initFigureLists() {
        allFromColor = Arrays.stream(Figure.values()).filter(f -> f.getColor().equals(this))
                .collect(Collectors.toList());
        allPawns = allFromColor.stream().filter(f -> f.getTerm() == 'B')
                .collect(Collectors.toList());
        allWithoutPawn = allFromColor.stream().filter(f -> f.getTerm() != 'B')
                .collect(Collectors.toList());
        allWithoutKing = allFromColor.stream().filter(f -> f.getTerm() != 'K')
                .collect(Collectors.toList());
    }

    public Figure king() {
        if (this.equals(ChessColor.WHITE)) {
            return Figure.KW;
        } else if (this.equals(ChessColor.BLACK)) {
            return Figure.KB;
        } else {
            throw new IllegalArgumentException("No king specified for empty!");
        }
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public ChessColor oppositColor() {
        return this.equals(ChessColor.BLACK)
                ? ChessColor.WHITE
                : this.equals(ChessColor.WHITE) ? ChessColor.BLACK : ChessColor.EMPTY;
    }
}
