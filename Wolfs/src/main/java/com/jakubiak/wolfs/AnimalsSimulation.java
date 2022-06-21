package com.jakubiak.wolfs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.jakubiak.wolfs.gui.GUIBoard;
import com.jakubiak.wolfs.logic.LogicBoard;
import com.jakubiak.wolfs.model.Parameters;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AnimalsSimulation {


    public static void main(String[] args) throws InterruptedException {

        var mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        Parameters parameters = null;
        try {
            parameters = mapper.readValue(new File("Wolfs\\\\src\\\\main\\\\resources\\\\parameters.yml"), Parameters.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // only 1 wolf supported
        final int numberOfWolfs = 1;

        int numberOfSheep = parameters != null ? parameters.numberOfSheep() : 12;
        int boardHeight = parameters != null ? parameters.boardHeight() : 10;
        int boardWidth = parameters != null ? parameters.boardWidth() : 10;
        int delay = parameters != null ?  parameters.delay() : 1000;

        GUIBoard GUIBoard = new GUIBoard(boardWidth, boardHeight);
        GUIBoard.setVisible(true);

        LogicBoard board = new LogicBoard(boardWidth, boardHeight, GUIBoard);
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < numberOfSheep; i++) {
            Thread sheepThread = board.addSheep(delay);
            threads.add(sheepThread);
        }
        for (int i = 0; i < numberOfWolfs; i++) {
            Thread wolfThread = board.addWolf(delay);
            threads.add(wolfThread);
        }

        //print animals on the GUI
        board.printAnimals();

        //start the threads
        threads.forEach(Thread::start);

        //wait for all threads to finish
        for (Thread thread : threads) {
            thread.join();
        }

        GUIBoard.setTitle(String.valueOf(board.getCountWolfMoves()));
    }
}


// in next versions make GUI to type parameters
//    public static void main(String[] args) {
//            InputWindow inputWindow = new InputWindow();
//
//            do {
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            } while (inputWindow.getParameters() == null);
//
//        AnimalsSimulation animalsSimulation = new AnimalsSimulation();
//        try {
//            animalsSimulation.runTheSimulation(inputWindow.getParameters());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public void runTheSimulation(Parameters parameters) throws InterruptedException {
//        // only 1 wolf supported
//        final int numberOfWolfs = 1;
//
//        GUIBoard GUIBoard = new GUIBoard(parameters.boardWidth(), parameters.boardHeight());
//        GUIBoard.setVisible(true);
//
//        LogicBoard board = new LogicBoard(parameters.boardWidth(), parameters.boardHeight(), GUIBoard);
//        ArrayList<Thread> threads = new ArrayList<>();
//        for (int i = 0; i < parameters.numberOfSheep(); i++) {
//            Thread sheepThread = board.addSheep(parameters.delay());
//            threads.add(sheepThread);
//        }
//        for (int i = 0; i < numberOfWolfs; i++) {
//            Thread wolfThread = board.addWolf(parameters.delay());
//            threads.add(wolfThread);
//        }
//
//        //print animals on the GUI
//        board.printAnimals();
//        //start the threads
//        threads.forEach(Thread::start);
//
//        //wait for all threads to finish
//        for (Thread thread : threads) {
//            thread.join();
//        }
//
//        GUIBoard.setTitle(String.valueOf(board.getCountWolfMoves()));
//    }
