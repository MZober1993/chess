package chess.tools.move;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Direction {

    public static final List<Direction> TOWER = Stream.of(new Direction(1, 0), new Direction(-1, 0),
            new Direction(0, 1), new Direction(0, -1))
            .collect(Collectors.toList());
    public static final List<Direction> BISCHOP = Stream.of(new Direction(1, 1), new Direction(-1, 1)
            , new Direction(-1, -1), new Direction(1, -1))
            .collect(Collectors.toList());
    public static final List<Direction> QUEEN = Stream.of(TOWER, BISCHOP).flatMap(Collection::stream)
            .collect(Collectors.toList());
    public static final List<Direction> SPRINGER = Stream.of(new Direction(1, 2), new Direction(2, 1), new Direction(1, -2), new Direction(2, -1),
            new Direction(-1, 2), new Direction(-2, 1), new Direction(-1, -2), new Direction(-2, -1))
            .collect(Collectors.toList());
    public static final List<Direction> PAWN_WHITE = Stream.of(new Direction(0, -1, false), new Direction(1, -1)
            , new Direction(-1, -1)).collect(Collectors.toList());
    public static final List<Direction> PAWN_BLACK = Stream.of(new Direction(0, 1, false), new Direction(1, 1)
            , new Direction(-1, 1)).collect(Collectors.toList());

    private final int i;
    private final int j;
    private boolean attackingMode = true;

    Direction(int i, int j) {
        this.i = i;
        this.j = j;
    }

    Direction(int i, int j, boolean attackingMode) {
        this(i, j);
        this.attackingMode = attackingMode;
    }

    public int getR() {
        return i;
    }

    public int getC() {
        return j;
    }

    public boolean isAttackingMode() {
        return attackingMode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Direction that = (Direction) o;

        if (i != that.i) return false;
        return j == that.j;

    }

    @Override
    public int hashCode() {
        int result = i;
        result = 31 * result + j;
        return result;
    }

    @Override
    public String toString() {
        return "[i:" + i + ",j:" + j + "]";
    }
}
