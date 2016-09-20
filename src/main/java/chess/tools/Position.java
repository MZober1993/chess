package chess.tools;

public class Position {
    private final int i;
    private final int j;
    private final boolean valid;

    Position(int i, int j) {

        this.i = Math.abs(i);
        this.j = Math.abs(j);
        valid = !(i > 7 || j > 7);
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
    public String toString() {
        return "Position{" +
                "i=" + i +
                ", j=" + j +
                '}';
    }
}
