package com.jakubiak.wolfs;

import com.jakubiak.wolfs.gui.GUIBoard;
import com.jakubiak.wolfs.model.Board;

import java.util.ArrayList;

public class AnimalsSimulation {

    public static void main(String[] args) throws InterruptedException {
        int numberOfSheep = 10;
        int numberOfWolfs = 1;
        int boardHeight = 12;
        int boardWidth = 12;
        int delay = 1000;

        GUIBoard GUIBoard = new GUIBoard(boardWidth, boardHeight);
        GUIBoard.setVisible(true);


        Board board = new Board(boardWidth, boardHeight, GUIBoard);
        var threads = new ArrayList<Thread>();
        for (int i = 0; i < numberOfSheep; i++) {
            Thread sheepThread = board.addSheep(delay);
            threads.add(sheepThread);
        }
        for (int i = 0; i < numberOfWolfs; i++) {
            Thread wolfThread = board.addWolf(delay);
            threads.add(wolfThread);
        }

        board.printAnimals();

        threads.forEach(Thread::start);

        for (Thread thread : threads) {
            thread.join();
        }
        GUIBoard.setTitle(String.valueOf(board.getCountWolfMoves()));
    }
}
