package com.jakubiak.wolfs.model;

import com.jakubiak.wolfs.logic.LogicBoard;
import com.jakubiak.wolfs.logic.RandomGenerator;

import java.util.Random;

public class Sheep extends Animal {
    public Sheep(int positionX, int positionY, int delay, LogicBoard board) {
        super(positionX, positionY, delay, board);
    }

    @Override
    public void run() {
        while (!this.isDead()) {
            System.out.println(Thread.currentThread() + " " + this);
            Random rand = RandomGenerator.getRandomGenerator();
            try {
                Thread.sleep((long) (rand.nextFloat(0.5f, 1.5f) * getDelay()));
                board.move(this);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
