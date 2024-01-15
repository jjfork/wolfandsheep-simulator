package com.jakubiak.wolfs.gui;

import javax.swing.*;
import java.awt.*;

public class GUIBoard extends JFrame {
    private final JButton[][] buttons;

    public GUIBoard(int width, int height) {
        super("Wolf Simulation");
        this.setLayout(new BorderLayout());
        this.setSize(width * 100, height * 100);

        JPanel board = new JPanel(new GridLayout(width, height));
        board.setVisible(true);
        buttons = new JButton[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setEnabled(false);
                buttons[i][j].setVisible(true);
                board.add(buttons[i][j]);
            }
        }
        this.add(board, BorderLayout.CENTER);
    }

    public void setWolf(int x, int y) {
        buttons[x][y].setText("W");
    }

    public void setSheep(int x, int y) {
        buttons[x][y].setText("S");
    }

    public void clear(int x, int y) {
        buttons[x][y].setText("");
    }
}
