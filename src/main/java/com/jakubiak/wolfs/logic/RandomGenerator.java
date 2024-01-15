package com.jakubiak.wolfs.logic;

import java.util.Random;

public class RandomGenerator {
    private static Random INSTANCE;

    private RandomGenerator() {
    }

    public static Random getRandomGenerator() {
        if (INSTANCE == null) {
            INSTANCE = new Random();
        }
        return INSTANCE;
    }
}
