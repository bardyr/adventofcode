package com.github.bardyr;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static com.github.bardyr.Utils.getInput;
import static org.junit.jupiter.api.Assertions.*;

class Day1Test {

    @Test
    void part1() throws IOException {
        String input = getInput("/day1/input.txt");

        List<String[]> list = Arrays.stream(input.split("\n")).map(i -> i.split(" {3}")).toList();

        List<Integer> right = new ArrayList<>();
        List<Integer> left = new ArrayList<>();

        list.forEach(i -> {
            left.add(Integer.parseInt(i[0]));
            right.add(Integer.parseInt(i[1]));
        });

        left.sort(Integer::compareTo);
        right.sort(Integer::compareTo);

        Integer distance = 0;
        for (int i = 0; i < left.size(); i++) {
            distance += (Math.abs(left.get(i) - right.get(i)));
        }

        System.out.println(distance);

        assertEquals(2742123, distance);
    }

    @Test
    void part2() throws IOException {
        String input = getInput("/day1_part2/input.txt");

        List<String[]> list = Arrays.stream(input.split("\n")).map(i -> i.split(" {3}")).toList();

        List<Integer> right = new ArrayList<>();
        List<Integer> left = new ArrayList<>();

        list.forEach(i -> {
            left.add(Integer.parseInt(i[0]));
            right.add(Integer.parseInt(i[1]));
        });

        AtomicReference<Long> similarity = new AtomicReference<>(0L);

        left.forEach(i -> similarity.updateAndGet(v -> v + i * right.stream().filter(i::equals).count()));

        assertEquals(21328497, similarity.get());
    }
}