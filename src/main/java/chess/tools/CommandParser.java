package chess.tools;

public class CommandParser {

    private static final int OFFSET_FOR_A = 97;
    private static final int OFFSET_ZERO = 49;
    private static final int MAX_ARRAY_OFFSET = 7;
    private static final MoveTuple FAIL_TUPLE = new MoveTuple(new Position(0, 0), new Position(0, 0), false);

    public MoveTuple parse(String command) {
        if (command.length() != 5) {
            return FAIL_TUPLE;
        }
        final String[] split = command.split(" ");
        Position begin = extractPosition(split[0]);
        Position end = extractPosition(split[1]);

        if (begin.isValid() && end.isValid()) {
            return new MoveTuple(begin, end, true);
        } else {
            return FAIL_TUPLE;
        }
    }

    private Position extractPosition(String term) {
        final int line = term.charAt(1) - OFFSET_ZERO;
        return new Position(OFFSET_FOR_A - term.charAt(0), MAX_ARRAY_OFFSET - line);
    }
}
