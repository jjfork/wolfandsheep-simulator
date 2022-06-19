package com.jakubiak.wolfs.gui;

import javax.swing.*;
import java.awt.*;

public class GUIBoard extends JFrame {
    private final JButton[][] bdSports;

    public GUIBoard(int width, int height) {
        super("Wolf Simulation");
        this.setLayout(new BorderLayout());
        this.setSize(width * 100, height * 100);

        JPanel board = new JPanel(new GridLayout(width, height));
        board.setVisible(true);
        bdSports = new JButton[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                bdSports[i][j] = new JButton("");
                bdSports[i][j].setEnabled(false);
                bdSports[i][j].setVisible(true);
                board.add(bdSports[i][j]);
            }
        }
        this.add(board, BorderLayout.CENTER);
    }

    public void setWolf(int x, int y) {
        bdSports[x][y].setText("W");
    }

    public void setSheep(int x, int y) {
        bdSports[x][y].setText("S");
    }

    public void clear(int x, int y) {
        bdSports[x][y].setText("");
    }
}
