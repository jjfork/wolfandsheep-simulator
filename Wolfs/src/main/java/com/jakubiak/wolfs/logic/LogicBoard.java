package com.jakubiak.wolfs.logic;

import com.jakubiak.wolfs.gui.GUIBoard;
import com.jakubiak.wolfs.model.Animal;
import com.jakubiak.wolfs.model.Position;
import com.jakubiak.wolfs.model.Sheep;
import com.jakubiak.wolfs.model.Wolf;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class LogicBoard implements LogicBoardUtils {
    private final int height;
    private final int width;
    private final ArrayList<Animal> animals;
    private final GUIBoard GUIBoard;
    private int countWolfMoves;

    public LogicBoard(int width, int height, GUIBoard GUIBoard) {
        this.height = height;
        this.width = width;
        this.GUIBoard = GUIBoard;
        animals = new ArrayList<>();
    }

    public int getCountWolfMoves() {
        return countWolfMoves;
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    //method to move, checking which animal and making their move
    //return true if sheep is killed
    public synchronized boolean move(Animal animal) {
        if (animal instanceof Wolf) {
            return moveWolf((Wolf) animal);
        } else {
            return moveSheep((Sheep) animal);
        }
    }

    //method to move wolf
    private boolean moveWolf(Wolf wolf) {
        countWolfMoves++;
        Sheep nearestSheep = nearestSheep(animals, wolf);
        GUIBoard.clear(wolf.x(), wolf.y());
        if (wolf.x() > nearestSheep.x()) {
            wolf.setPositionX(wolf.x() - 1);
        } else if (wolf.x() < nearestSheep.x()) {
            wolf.setPositionX(wolf.x() + 1);
        }
        if (wolf.y() > nearestSheep.y()) {
            wolf.setPositionY(wolf.y() - 1);
        } else if (wolf.y() < nearestSheep.y()) {
            wolf.setPositionY(wolf.y() + 1);
        }

        if (wolf.x() == nearestSheep.x()
                && wolf.y() == nearestSheep.y()) {
            nearestSheep.kill();
            GUIBoard.setWolf(wolf.x(), wolf.y());
            return true;
        }
        GUIBoard.setWolf(wolf.x(), wolf.y());
        return false;
    }

    //method to move sheep
    private boolean moveSheep(Sheep sheep) {
        int oldX = sheep.x();
        int oldY = sheep.y();
        int x = oldX;
        int y = oldY;

        if (this.getWolf().x() > sheep.x()) {
            x = sheep.x() - 1;
        } else if (this.getWolf().x() < sheep.x()) {
            x = sheep.x() + 1;
        }

        if (this.getWolf().y() > sheep.y()) {
            y = sheep.y() - 1;
        } else if (this.getWolf().y() < sheep.y()) {
            y = sheep.y() + 1;
        }

        Position newPosition = new Position(x, y);

        //when sheep is going to move out of board
        if (!isInBoard(height, width, newPosition)) {
            List<Position> freePositions = Stream.of(
                    new Position(sheep.x() - 1, sheep.y() - 1),
                    new Position(sheep.x() + 1, sheep.y() + 1),
                    new Position(sheep.x() - 1, sheep.y() + 1),
                    new Position(sheep.x() + 1, sheep.y() - 1),
                    new Position(sheep.x(), sheep.y() - 1),
                    new Position(sheep.x() - 1, sheep.y()),
                    new Position(sheep.x(), sheep.y() + 1),
                    new Position(sheep.x() + 1, sheep.y())
            ).filter(position -> isInBoard(height, width, position) && !isNotFree(animals, position))
                    .toList();
            //to not jump into other animal
            if (!freePositions.isEmpty()) {
                Random rand = RandomGenerator.getRandomGenerator();
                int i = rand.nextInt(freePositions.size());
                sheep.setPositionY(freePositions.get(i).y());
                sheep.setPositionX(freePositions.get(i).x());
                GUIBoard.clear(oldX, oldY);
                GUIBoard.setSheep(freePositions.get(i).x(), freePositions.get(i).y());
            }
        } else if (!isNotFree(animals, newPosition)) {
            sheep.setPositionY(newPosition.y());
            sheep.setPositionX(newPosition.x());
            GUIBoard.clear(oldX, oldY);
            GUIBoard.setSheep(newPosition.x(), newPosition.y());
        }
        return false;
    }

    public Wolf getWolf() {
        return (Wolf) animals.stream()
                .filter(animal -> animal instanceof Wolf)
                .findFirst()
                .get();
    }

    public Thread addWolf(int delay) {
        Position position = getFreePosition(animals, height, width);
        Wolf wolf = new Wolf(position.x(), position.y(), delay, this);
        animals.add(wolf);
        return new Thread(wolf);
    }

    public Thread addSheep(int delay) {
        Position position = getFreePosition(animals, height, width);
        Sheep sheep = new Sheep(position.x(), position.y(), delay, this);
        animals.add(sheep);
        return new Thread(sheep);
    }

    public void printAnimals() {
        for (int i = 0; i < getSheep(animals).size(); i++) {
            GUIBoard.setSheep(getSheep(animals).get(i).x(), getSheep(animals).get(i).y());
        }
        GUIBoard.setWolf(getWolf().x(), getWolf().y());
    }
}
