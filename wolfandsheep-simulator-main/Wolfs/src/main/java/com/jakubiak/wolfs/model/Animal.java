package com.jakubiak.wolfs.model;

import com.jakubiak.wolfs.logic.LogicBoard;

public abstract class Animal implements Runnable {
    protected final LogicBoard board;
    private final int delay;
    private int positionX;
    private int positionY;
    private boolean isDead;

    public Animal(int positionX, int positionY, int delay, LogicBoard board) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.delay = delay;
        this.board = board;
        this.isDead = false;
    }

    public boolean isDead() {
        return isDead;
    }

    public void kill() {
        this.isDead = true;
    }

    public LogicBoard getBoard() {
        return board;
    }

    public int getDelay() {
        return delay;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "X=" + positionX +
                ", Y=" + positionY +
                '}';
    }

    public int x() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int y() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }
}
