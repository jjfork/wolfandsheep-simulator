package com.jakubiak.wolfs.model;

import com.jakubiak.wolfs.logic.LogicBoard;
import com.jakubiak.wolfs.logic.RandomGenerator;

import java.util.Random;

public class Wolf extends Animal {
    public Wolf(int positionX, int positionY, int delay, LogicBoard board) {
        super(positionX, positionY, delay, board);
    }

    @Override
    public void run() {
        while (sheepAreAlive()) {
            Random rand = RandomGenerator.getRandomGenerator();
            try {
                Thread.sleep((long) (rand.nextFloat(0.5f, 1.5f) * getDelay()));
                boolean killedSheep = board.move(this);
                if (killedSheep && sheepAreAlive()) {
                    System.out.println("KILLED KILLED KILLED KILLED KILLED KILLED KILLED");
                    for (int i = 0; i < 5; i++) {
                        Thread.sleep((long) (rand.nextFloat(0.5f, 1.5f) * getDelay()));
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread() + " Wolf " + this);
        }
    }

    private boolean sheepAreAlive() {
        return getBoard().getSheep(board.getAnimals()).size() > 0;
    }
}
