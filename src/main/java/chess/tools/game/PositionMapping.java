package chess.tools.game;

import chess.tools.move.Position;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public void printList(List<Position> positions) {
        System.out.println("positions: " + positions.stream().map(pos -> commandMapping.get(pos)).collect(Collectors.toList()));
    }

    public Position calcPosition(int index) {
        final int i = index / 8;
        final int j = index % 8;
        return new Position(j, i);
    }

    public String calcCommand(int index) {
        return commandMapping.get(calcPosition(index));
    }
}
