package chess.tools.move;

public class Position {
    private final int i;
    private final int j;
    private final boolean valid;
    private final int index;

    public Position(int i, int j) {
        this.j = Math.abs(j);
        this.i = Math.abs(i);
        this.index = i + 8 * j;
        valid = !(j > 7 || i > 7);
    }

    public Position(int index) {
        this.index = index;
        this.j = Math.abs(index % 8);
        this.i = Math.abs(index / 8);
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

    public int getIndex() {
        return index;
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
        return "[i:" + i + ",j:" + j + ",id:" + index + "]";
    }
}
