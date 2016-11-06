package chess.tools.game;

import javax.swing.*;

public class FigureLabel extends JLabel {
    public FigureLabel(Figure figure) {
        super(figure.toString());
        this.setForeground(figure.getColor().getColor());
    }
}
