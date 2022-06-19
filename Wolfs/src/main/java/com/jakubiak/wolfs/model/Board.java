package com.jakubiak.wolfs.model;

import com.jakubiak.wolfs.gui.GUIBoard;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class Board {
    private final int height;
    private final int width;
    private final ArrayList<Animal> animals;
    private final GUIBoard GUIBoard;

    public int getCountWolfMoves() {
        return countWolfMoves;
    }

    private int countWolfMoves;

    public Board(int width, int height, GUIBoard GUIBoard) {
        this.height = height;
        this.width = width;
        animals = new ArrayList<>();
        this.GUIBoard = GUIBoard;
    }

    public synchronized boolean move(boolean isWolf, Animal animal) {
        if (isWolf) {
            return moveWolf(animal);
        } else {
            return moveSheep(animal);
        }
    }

    private boolean moveWolf(Animal animal) {
        countWolfMoves++;
        Sheep nearestSheep = nearestSheep((Wolf) animal);
        GUIBoard.clear(animal.getPositionX(), animal.getPositionY());
        if (animal.getPositionX() > nearestSheep.getPositionX()) {
            animal.setPositionX(animal.getPositionX() - 1);
        } else if (animal.getPositionX() < nearestSheep.getPositionX()) {
            animal.setPositionX(animal.getPositionX() + 1);
        }
        if (animal.getPositionY() > nearestSheep.getPositionY()) {
            animal.setPositionY(animal.getPositionY() - 1);
        } else if (animal.getPositionY() < nearestSheep.getPositionY()) {
            animal.setPositionY(animal.getPositionY() + 1);
        }

        if (animal.getPositionX() == nearestSheep.getPositionX()
                && animal.getPositionY() == nearestSheep.getPositionY()) {
            nearestSheep.kill();
            GUIBoard.setWolf(animal.getPositionX(), animal.getPositionY());
            return true;
        }
        GUIBoard.setWolf(animal.getPositionX(), animal.getPositionY());
        return false;
    }

    private Sheep nearestSheep(Wolf wolf) {
        List<Sheep> sheep = getSheep();
        List<Pair<Sheep, Double>> mappedSheep = sheep.stream()
                .map(singleSheep -> Pair.of(singleSheep,
                        distance(wolf.getPositionX(), wolf.getPositionY(),
                                singleSheep.getPositionX(), singleSheep.getPositionY())))
                .toList();

        double minDistance = Double.MAX_VALUE;
        Sheep locNearestSheep = null;
        for (Pair<Sheep, Double> sheepDoublePair : mappedSheep) {
            if (minDistance > sheepDoublePair.getRight()) {
                minDistance = sheepDoublePair.getRight();
                locNearestSheep = sheepDoublePair.getLeft();
            }

        }
        return locNearestSheep;
    }

    private double distance(int x1, int y1, int x2, int y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }


    private boolean moveSheep(Animal animal) {
        int oldX = animal.getPositionX();
        int oldY = animal.getPositionY();
        int x = animal.getPositionX();
        int y = animal.getPositionY();

        if (this.getWolf().getPositionX() > animal.getPositionX()) {
            x = animal.getPositionX() - 1;
        } else if (this.getWolf().getPositionX() < animal.getPositionX()) {
            x = animal.getPositionX() + 1;
        }

        if (this.getWolf().getPositionY() > animal.getPositionY()) {
            y = animal.getPositionY() - 1;
        } else if (this.getWolf().getPositionY() < animal.getPositionY()) {
            y = animal.getPositionY() + 1;
        }

        Position newPosition = new Position(x, y);

        if (!isInBoard(newPosition)) {
            List<Position> freePositions = Stream.of(
                            new Position(animal.getPositionX() - 1, animal.getPositionY() - 1),
                            new Position(animal.getPositionX() + 1, animal.getPositionY() + 1),
                            new Position(animal.getPositionX() - 1, animal.getPositionY() + 1),
                            new Position(animal.getPositionX() + 1, animal.getPositionY() - 1),
                            new Position(animal.getPositionX(), animal.getPositionY() - 1),
                            new Position(animal.getPositionX() - 1, animal.getPositionY()),
                            new Position(animal.getPositionX(), animal.getPositionY() + 1),
                            new Position(animal.getPositionX() + 1, animal.getPositionY())
                    ).filter(position -> isInBoard(position) && !isNotFree(position))
                    .toList();

            if (!freePositions.isEmpty()) {
                Random rand = new Random();
                int i = rand.nextInt(freePositions.size());
                animal.setPositionY(freePositions.get(i).y());
                animal.setPositionX(freePositions.get(i).x());
                GUIBoard.clear(oldX, oldY);
                GUIBoard.setSheep(freePositions.get(i).x(), freePositions.get(i).y());
            }
        } else if (!isNotFree(newPosition)) {
            animal.setPositionY(newPosition.y());
            animal.setPositionX(newPosition.x());
            GUIBoard.clear(oldX, oldY);
            GUIBoard.setSheep(newPosition.x(), newPosition.y());
        }

        return false;
    }

    private boolean isInBoard(Position newPosition) {
        return newPosition.y() < height && newPosition.x() < width && newPosition.x() >= 0 && newPosition.y() >= 0;
    }

    public List<Sheep> getSheep() {
        return animals.stream()
                .filter(animal -> animal instanceof Sheep)
                .filter(animal -> !animal.isDead())
                .map((sheep -> (Sheep) sheep))
                .toList();

    }

    public Wolf getWolf() {
        return (Wolf) animals.stream()
                .filter(animal -> animal instanceof Wolf)
                .findFirst()
                .get();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }


    public Thread addWolf(int delay) {
        Position position = getFreePosition();
        Wolf wolf = new Wolf(position.x(), position.y(), delay, this);
        animals.add(wolf);
        return new Thread(wolf);
    }

    public Thread addSheep(int delay) {
        Position position = getFreePosition();
        Sheep sheep = new Sheep(position.x(), position.y(), delay, this);
        animals.add(sheep);
        return new Thread(sheep);
    }

    private Position getFreePosition() {
        Random rand = new Random();

        Position position;
        do {
            position = new Position(rand.nextInt(width), rand.nextInt(height));
        } while (isNotFree(position));

        return position;
    }

    private boolean isNotFree(Position position) {
        List<Position> positions = animals.stream()
                .map(animal -> new Position(animal.getPositionX(), animal.getPositionY()))
                .toList();

        return positions.contains(position);
    }


    public void printAnimals() {
        for (int i = 0; i < getSheep().size(); i++) {
            GUIBoard.setSheep(getSheep().get(i).getPositionX(), getSheep().get(i).getPositionY());
        }
        GUIBoard.setWolf(getWolf().getPositionX(), getWolf().getPositionY());
    }
}
