package chess.tools;

public class MoveTuple {
    private final Position begin;
    private final Position end;
    private final boolean possible;

    MoveTuple(Position begin, Position end, boolean possible) {

        this.begin = begin;
        this.end = end;
        this.possible = possible;
    }

    public Position getBegin() {
        return begin;
    }

    public Position getEnd() {
        return end;
    }

    public boolean isPossible() {
        return possible;
    }

    @Override
    public String toString() {
        return "MoveTuple{" +
                "begin=" + begin +
                ", end=" + end +
                ", possible=" + possible +
                '}';
    }
}
