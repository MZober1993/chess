package chess.tools.move;

public class Position {
    private final int i;
    private final int j;
    private final boolean valid;

    public Position(int i, int j) {

        this.i = Math.abs(i);
        this.j = Math.abs(j);
        valid = !(i > 7 || j > 7);
    }

    Position(Position position) {
        this(position.getR(), position.getC());
    }

    public int getR() {
        return i;
    }

    public int getC() {
        return j;
    }

    public boolean isValid() {
        return valid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (i != position.i) return false;
        return j == position.j;

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
