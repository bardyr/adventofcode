package com.github.bardyr;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day8Test {

    @Test
    void part1() throws URISyntaxException, IOException {
        var content = Files.readAllLines(Path.of(Objects.requireNonNull(this.getClass().getResource("/day8/input.txt")).toURI()));

        final List<Coordinate> coordinates = new ArrayList<>();

        for (int i = 0; i < content.size(); i++) {
            for (int j = 0; j < content.get(i).length(); j++) {
                char c = content.get(i).charAt(j);
                coordinates.add(new Coordinate(j, i, c));
            }
        }

        Map<Character, List<Coordinate>> collect = coordinates.stream()
                .filter(Coordinate::isAntenna)
                .collect(groupingBy(d -> d.c, Collectors.toList()));

        collect.forEach((k, v) -> {
            System.out.println(k + " " + v.size());

            for (int i = 0; i < v.size(); i++) {
                for (Coordinate value : v) {

                    if (v.get(i).equals(value)) {
                        continue;
                    }

                    int deltaX = 2 * value.x - v.get(i).x;
                    int deltaY = 2 * value.y - v.get(i).y;

                    //     System.out.println("DeltaX: " + deltaX + " DeltaY: " + deltaY);

                    coordinates.stream()
                            .filter(c -> c.x == deltaX && c.y == deltaY)
                            .forEach(c -> {
                                c.antinodes++;
                                //    System.out.println("Antinode: " + (char) c.c + " " + c.x + " " + c.y);
                            });

                }
            }

        });


        long count = coordinates.stream()
                .filter(Coordinate::isAntiNode)
                .distinct()
                .count();


        System.out.println(collect.keySet());

        printCoordinates(coordinates);

        assertEquals(285, count);

    }

    @Test
    void part2() throws URISyntaxException, IOException {
        var content = Files.readAllLines(Path.of(Objects.requireNonNull(this.getClass().getResource("/day8/input.txt")).toURI()));

        final List<Coordinate> coordinates = new ArrayList<>();

        for (int i = 0; i < content.size(); i++) {
            for (int j = 0; j < content.get(i).length(); j++) {
                char c = content.get(i).charAt(j);
                coordinates.add(new Coordinate(j, i, c));
            }
        }

        Map<Character, List<Coordinate>> collect = coordinates.stream()
                .filter(Coordinate::isAntenna)
                .collect(groupingBy(d -> d.c, Collectors.toList()));

        collect.forEach((k, v) -> {

            for (int i = 0; i < v.size(); i++) {
                for (Coordinate value : v) {

                    if (v.get(i).equals(value)) {
                        continue;
                    }

                    Coordinate coordinate = value;
                    int deltaX = v.get(i).x - coordinate.x;
                    int deltaY = v.get(i).y - coordinate.y;

                    while (true) {
                        int x = coordinate.x + deltaX;
                        int y = coordinate.y + deltaY;


                        Optional<Coordinate> first = coordinates.stream()
                                .filter(c -> c.x == x && c.y == y)
                                .findFirst();

                        first.ifPresent(coordinate1 -> coordinate1.antinodes++);

                        if (first.isEmpty()) {
                            break;
                        }

                        coordinate = first.get();
                    }

                }
            }

        });


        long count = coordinates.stream()
                .filter(Coordinate::isAntiNode)
                .distinct()
                .count();


        System.out.println(collect.keySet());

        printCoordinates(coordinates);

        assertEquals(944, count);

    }

    public void printCoordinates(List<Coordinate> coordinates) {
        coordinates.forEach(coordinate -> {
            if (coordinate.x == 0) {
                System.out.println();
            }

            if (coordinate.isAntiNode()) {
                System.out.print('#');
            } else {

                System.out.print(coordinate.c);
            }
        });
    }

    static class Coordinate {
        int x;
        int y;
        char c;
        int antinodes = 0;

        public Coordinate(int x, int y, char c) {
            this.x = x;
            this.y = y;
            this.c = c;
        }

        boolean isAntenna() {
            return StringUtils.isAlphanumeric(String.valueOf(c));
        }

        @Override
        public String toString() {
            return "Coordinate{" +
                    "x=" + x +
                    ", y=" + y +
                    ", c=" + c +
                    '}';
        }

        public boolean isAntiNode() {
            return antinodes > 0;
        }
    }
}
