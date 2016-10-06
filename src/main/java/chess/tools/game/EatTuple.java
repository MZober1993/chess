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
    public String toString() {
        return "EatTuple{" +
                "attacker=" + attacker +
                ", loser=" + loser +
                ", turnCount=" + turnCount +
                '}';
    }
}
