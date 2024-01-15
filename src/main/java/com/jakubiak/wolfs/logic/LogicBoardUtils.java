package com.jakubiak.wolfs.logic;

import com.jakubiak.wolfs.model.Animal;
import com.jakubiak.wolfs.model.Position;
import com.jakubiak.wolfs.model.Sheep;
import com.jakubiak.wolfs.model.Wolf;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

interface LogicBoardUtils {

    //method to check where is nearest sheep
    default Sheep nearestSheep(ArrayList<Animal> animals, Wolf wolf) {
        List<Sheep> sheep = getSheep(animals);
        List<Pair<Sheep, Double>> mappedSheep = sheep.stream()
                .map(singleSheep -> {
                    double distance = distance(wolf.x(), wolf.y(), singleSheep.x(), singleSheep.y());
                    return Pair.of(singleSheep, distance);
                })
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

    //calculating distance
    default double distance(int x1, int y1, int x2, int y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    default List<Sheep> getSheep(ArrayList<Animal> animals) {
        return animals.stream()
                .filter(animal -> animal instanceof Sheep)
                .filter(animal -> !animal.isDead())
                .map((sheep -> (Sheep) sheep))
                .toList();
    }

    //checking is on board
    default boolean isInBoard(int height, int width, Position newPosition) {
        return newPosition.y() < height && newPosition.x() < width && newPosition.x() >= 0 && newPosition.y() >= 0;
    }

    default Position getFreePosition(ArrayList<Animal> animals, int height, int width) {
        Random rand = RandomGenerator.getRandomGenerator();

        Position position;
        do {
            position = new Position(rand.nextInt(width), rand.nextInt(height));
        } while (isNotFree(animals, position));

        return position;
    }

    default boolean isNotFree(ArrayList<Animal> animals, Position position) {
        List<Position> positions = animals.stream()
                .map(animal -> new Position(animal.x(), animal.y()))
                .toList();

        return positions.contains(position);
    }
}
