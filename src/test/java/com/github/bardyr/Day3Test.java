package com.github.bardyr;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day3Test {
    @Test
    void day3_part1() throws IOException, URISyntaxException {
        var content = Files.readString(Path.of(Objects.requireNonNull(this.getClass().getResource("/day3/part1/input.txt")).toURI()));



        String regex = "mul\\((\\d{1,3}),(\\d{1,3})\\)";

        AtomicInteger sum = new AtomicInteger();

        Pattern.compile(regex).matcher(content).results().forEach(i -> {
            int group1 = Integer.parseInt(i.group(1));
            int group2 = Integer.parseInt(i.group(2));
            sum.addAndGet(group1 * group2);
        });


        assertEquals(170778545, sum.get());
    }

    @Test
    void day3_part2() throws IOException, URISyntaxException {
        var content = Files.readString(Path.of(Objects.requireNonNull(this.getClass().getResource("/day3/part1/input.txt")).toURI()));

        String regex = "mul\\((\\d{1,3}),(\\d{1,3})\\)|(do\\(\\)|don't\\(\\))";

        AtomicInteger sum = new AtomicInteger();

        boolean enabled = true;

        Matcher matcher = Pattern.compile(regex).matcher(content);
        while (matcher.find()) {

            if ("do()".equals(matcher.group(3))) {
                enabled = true;
            } else if ("don't()".equals(matcher.group(3))) {
                enabled = false;
            }

            if (enabled && matcher.group(1) != null && matcher.group(2) != null) {
                int group1 = Integer.parseInt(matcher.group(1));
                int group2 = Integer.parseInt(matcher.group(2));
                sum.addAndGet(group1 * group2);
            }
        }

        assertEquals(82868252, sum.get());
    }
}