package com.game.utils;

import java.util.Random;

public class RandomGenerator {
    public static float nextFloat(float min, float max)
    {
        return min + new Random().nextFloat() * (max - min);
    }
}
