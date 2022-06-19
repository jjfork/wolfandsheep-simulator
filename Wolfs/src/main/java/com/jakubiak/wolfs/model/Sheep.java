package com.jakubiak.wolfs.model;

import java.util.Random;

public class Sheep extends Animal {
    public Sheep(int positionX, int positionY, int delay, Board board) {
        super(positionX, positionY, delay, board);
    }

    @Override
    public void run() {
        while (!this.isDead()) {
            System.out.println(Thread.currentThread() + " " + this);
            Random rand = new Random();
            try {
                Thread.sleep((long) (rand.nextFloat(0.5f, 1.5f) * getDelay()));
                board.move(false, this);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
