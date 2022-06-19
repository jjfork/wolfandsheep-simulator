package com.jakubiak.wolfs.model;

import java.util.Random;

public class Wolf extends Animal {
    public Wolf(int positionX, int positionY, int delay, Board board) {
        super(positionX, positionY, delay, board);
    }

    @Override
    public void run() {
        while (getBoard().getSheep().size() > 0) {
            Random rand = new Random();
            try {
                Thread.sleep((long) (rand.nextFloat(0.5f, 1.5f) * getDelay()));
                boolean killedSheep = board.move(true, this);
                if (killedSheep) {
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
}
