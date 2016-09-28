package chess.tools.game;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Color {
    BLACK, WHITE, EMPTY;

    public List<Figure> allFromColor() {
        return Arrays.stream(Figure.values()).filter(f -> f.getColor().equals(this))
                .collect(Collectors.toList());
    }

    public List<Figure> allWithoutPawn() {
        return Arrays.stream(Figure.values()).filter(f -> f.getColor().equals(this) && f.getTerm() != 'B')
                .collect(Collectors.toList());
    }
}
