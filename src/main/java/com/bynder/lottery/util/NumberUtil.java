package com.bynder.lottery.util;

import java.util.Random;

public class NumberUtil {

    public static long randomBetween(long minInclusive, long maxInclusive) {
        Random random = new Random();
        return random
            .longs(minInclusive, maxInclusive + 1)
            .findFirst()
            .getAsLong();
    }
}
