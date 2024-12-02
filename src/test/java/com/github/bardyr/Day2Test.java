package com.github.bardyr;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;

import static com.github.bardyr.Utils.getPath;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Day2Test {

    @Test
    void part1() throws IOException, URISyntaxException {

        var lines = Files.readAllLines(Path.of(Objects.requireNonNull(this.getClass().getResource("/day2/part1/input.txt")).toURI()))
                .stream()
                .map(i -> i.split(" "))
                .map(Utils::convertListToInt)
                .toList();

        int unsafe = 0;
        for (int[] line : lines) {

            int leaning = 0;

            for (int i = 0; i < line.length - 1; i++) {
                int diff = line[i] - line[i + 1];

                if (diff > 0 && (leaning == 0 || leaning == 1)) {
                    leaning = 1;
                } else if (diff < 0 && (leaning == 0 || leaning == -1)) {
                    leaning = -1;
                } else {
                    System.out.println("Unsafe line: " + Arrays.toString(line));
                    unsafe++;
                    break;
                }

                if (Math.abs(diff) > 3) {
                    System.out.println("Unsafe line: " + Arrays.toString(line));
                    unsafe++;
                    break;
                }
            }
        }

        assertEquals(332, lines.size() - unsafe);
    }

    @Test
    void part1_better() throws IOException {

        var safe = Files.readAllLines(getPath("/day2/part1/input.txt"))
                .stream()
                .map(Report::of)
                .filter(Report::isSafe)
                .count();

        assertEquals(332, safe);
    }

    @Test
    void part2() {
        var safe = Utils.getLines("/day2/part1/input.txt")
                .map(Report::of)
                .filter(Report::isSafer)
                .count();

        assertEquals(398, safe);
    }
}