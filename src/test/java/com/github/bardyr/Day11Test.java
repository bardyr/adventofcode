package com.github.bardyr;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class Day11Test {

    @Test
    void cacheThatShit() {
        List<Long> list = List.of(9694820L, 93L, 54276L, 1304L, 314L, 664481L, 0L, 4L);

        HashMap<String, Long> cache = new HashMap<>();
        long sum25 = list.stream()
                .mapToLong(l -> blinkRecursive(l, 25, cache))
                .sum();

        long sum75 = list.stream()
                .mapToLong(l -> blinkRecursive(l, 75, cache))
                .sum();

        assertEquals(185894L, sum25);
        assertEquals(221632504974231L, sum75);
    }

    private long blinkRecursive(Long l, int i, HashMap<String, Long> cache) {

        String key = l.toString() + "-" + i;
        Long l2 = cache.get(key);
        if (l2 != null) {
            return l2;
        }

        if (i == 0) {
            return 1;
        }

        List<Long> blinks = blink(l);

        long sum = blinks.stream()
                .mapToLong(ll -> blinkRecursive(ll, i - 1, cache))
                .sum();

        cache.put(key, sum);

        return sum;
    }

    private List<Long> blink(Long next) {
        String lString = Long.toString(next);
        int length = lString.length();

        if (next == 0L) {
            return List.of(1L);
        } else if (length % 2 == 0) {
            long l = Long.parseLong(lString.substring(0, length / 2));
            long r = Long.parseLong(lString.substring(length / 2));
            return List.of(l, r);
        } else {
            return List.of(next * 2024);
        }
    }
}

