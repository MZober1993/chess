package chess.tools.move;

public class Position {
    private final int i;
    private final int j;
    private final boolean valid;

    public Position(int i, int j) {

        this.j = Math.abs(j);
        this.i = Math.abs(i);
        valid = !(j > 7 || i > 7);
    }

    public int getR() {
        return j;
    }

    public int getC() {
        return i;
    }

    public boolean isValid() {
        return valid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (j != position.j) return false;
        return i == position.i;

    }

    @Override
    public int hashCode() {
        int result = j;
        result = 31 * result + i;
        return result;
    }

    @Override
    public String toString() {
        return "[i:" + i + ",j:" + j + "]";
    }
}
