package chess.tools.game.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Logger;

public class ChessSquare extends JPanel implements MouseListener {

    private int index;
    private final Color color;
    private boolean chosen = false;
    private Logger logger = Logger.getLogger(ChessSquare.class.getName());

    public ChessSquare(int index) {

        this.index = index;
        addMouseListener(this);
        color = generalColor();
        setBackground(color);
    }

    private Color generalColor() {
        int row = (index / 8) % 2;
        if (row == 0) {
            return index % 2 == 0 ? Color.black : Color.white;
        } else {
            return index % 2 == 0 ? Color.white : Color.black;
        }
    }

    public void setBackgroundToGeneral() {
        setBackground(color);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if (EventBus.CHESS.registerSquare(this)) {
            logger.info("choose: " + index);
            setBackground(Color.green);
            chosen = true;
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
        if (!chosen) {
            setBackground(Color.blue);
        }
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        if (!chosen) {
            setBackground(color);
        }
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isChosen() {
        return chosen;
    }

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
    }
}
