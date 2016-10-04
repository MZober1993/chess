package chess.tools.game;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ChessColor {
    BLACK(Color.darkGray), WHITE(Color.lightGray), EMPTY(Color.gray);

    private Color color;

    ChessColor(Color color) {
        this.color = color;
    }

    public List<Figure> allFromColor() {
        return Arrays.stream(Figure.values()).filter(f -> f.getColor().equals(this))
                .collect(Collectors.toList());
    }

    public List<Figure> allWithoutPawn() {
        return Arrays.stream(Figure.values()).filter(f -> f.getColor().equals(this) && f.getTerm() != 'B')
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
