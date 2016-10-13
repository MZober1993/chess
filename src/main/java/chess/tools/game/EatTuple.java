package chess.tools.game;

public class EatTuple {

    private final Figure attacker;
    private final Figure loser;
    private int turnCount;

    public EatTuple(Figure attacker, Figure loser, int turnCount) {

        this.attacker = attacker;
        this.loser = loser;
        this.turnCount = turnCount;
    }

    public Figure getAttacker() {
        return attacker;
    }

    public Figure getLoser() {
        return loser;
    }

    public int getTurnCount() {
        return turnCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EatTuple eatTuple = (EatTuple) o;

        if (turnCount != eatTuple.turnCount) return false;
        if (attacker != eatTuple.attacker) return false;
        return loser == eatTuple.loser;

    }

    @Override
    public int hashCode() {
        int result = attacker != null ? attacker.hashCode() : 0;
        result = 31 * result + (loser != null ? loser.hashCode() : 0);
        result = 31 * result + turnCount;
        return result;
    }

    @Override
    public String toString() {
        return "EatTuple{" +
                "attacker=" + attacker +
                ", loser=" + loser +
                ", turnCount=" + turnCount +
                '}';
    }
}
