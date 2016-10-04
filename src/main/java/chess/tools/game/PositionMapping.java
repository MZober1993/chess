package chess.tools.game;

import chess.tools.move.Position;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PositionMapping {
    private Map<Position, String> commandMapping = new HashMap<>();

    PositionMapping() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Position pos = new Position(j, i);
                final int line = 8 - j;
                List<String> rows = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h");
                commandMapping.put(pos, rows.get(i) + line);
            }
        }
    }

    public Map<Position, String> getCommandMapping() {
        return commandMapping;
    }
}
