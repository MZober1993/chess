package chess.tools.game.gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    JLayeredPane layeredPane;
    JPanel chessBoard;

    public MainFrame() {
        Dimension boardSize = new Dimension(600, 600);

        layeredPane = new JLayeredPane();
        getContentPane().add(layeredPane);
        layeredPane.setPreferredSize(boardSize);

        chessBoard = new JPanel();
        layeredPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER);
        chessBoard.setLayout(new GridLayout(8, 8));
        chessBoard.setPreferredSize(boardSize);
        chessBoard.setBounds(0, 0, boardSize.width, boardSize.height);

        EventBus.CHESS.initGuiWithChess(chessBoard);
    }

    public static void main(String[] args) {
        JFrame frame = new MainFrame();
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}