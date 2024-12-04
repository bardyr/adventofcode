package com.github.bardyr;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day4Test {
    @Test
    void day4_part1() throws IOException, URISyntaxException {
        var content = Files.readAllLines(Path.of(Objects.requireNonNull(this.getClass().getResource("/day4/part1_input.txt")).toURI()));

        /*
        String content = """
                ....XXMAS.
                .SAMXMS...
                ...S..A...
                ..A.A.MS.X
                XMASAMX.MM
                X.....XA.A
                S.S.S.S.SS
                .A.A.A.A.A
                ..M.M.M.MM
                .X.X.XMASX""";
        */

        List<Coordinate> coordinates = new ArrayList<>();

        for (int i = 0; i < content.size(); i++) {
            for (int j = 0; j < content.get(i).length(); j++) {
                char c = content.get(i).charAt(j);

                coordinates.add(new Coordinate(j, i, c));

            }
        }

        int sumOfXmas = 0;

        List<Coordinate> exes = coordinates.stream().filter(i -> i.c == 'X').toList();

        for (Coordinate x : exes) {

            {
                Optional<Coordinate> m = findRight(x, 'M', coordinates);

                if (m.isPresent()) {
                    var a = findRight(m.get(), 'A', coordinates);
                    if (a.isPresent()) {
                        var s = findRight(a.get(), 'S', coordinates);
                        if (s.isPresent()) {
                            System.out.println("Found XMAS");
                            sumOfXmas++;

                            x.visited(true);
                            m.ifPresent(i -> i.visited(true));
                            a.ifPresent(i -> i.visited(true));
                            s.ifPresent(i -> i.visited(true));
                        }
                    }
                }
            }

            {

                Optional<Coordinate> m = findLeft(x, 'M', coordinates);

                if (m.isPresent()) {
                    var a = findLeft(m.get(), 'A', coordinates);
                    if (a.isPresent()) {
                        var s = findLeft(a.get(), 'S', coordinates);
                        if (s.isPresent()) {
                            System.out.println("Found XMAS");

                            sumOfXmas++;
                            x.visited(true);
                            m.ifPresent(i -> i.visited(true));
                            a.ifPresent(i -> i.visited(true));
                            s.ifPresent(i -> i.visited(true));
                        }
                    }
                }
            }

            {

                Optional<Coordinate> m = findUp(x, 'M', coordinates);

                if (m.isPresent()) {
                    var a = findUp(m.get(), 'A', coordinates);
                    if (a.isPresent()) {
                        var s = findUp(a.get(), 'S', coordinates);
                        if (s.isPresent()) {
                            System.out.println("Found XMAS");

                            sumOfXmas++;

                            x.visited(true);
                            m.ifPresent(i -> i.visited(true));
                            a.ifPresent(i -> i.visited(true));
                            s.ifPresent(i -> i.visited(true));
                        }
                    }
                }
            }

            {

                Optional<Coordinate> m = findDown(x, 'M', coordinates);

                if (m.isPresent()) {
                    var a = findDown(m.get(), 'A', coordinates);
                    if (a.isPresent()) {
                        var s = findDown(a.get(), 'S', coordinates);
                        if (s.isPresent()) {
                            System.out.println("Found XMAS");
                            sumOfXmas++;

                            x.visited(true);
                            m.ifPresent(i -> i.visited(true));
                            a.ifPresent(i -> i.visited(true));
                            s.ifPresent(i -> i.visited(true));
                        }
                    }
                }
            }

            {

                Optional<Coordinate> m = findDiagonalDownRight(x, 'M', coordinates);

                if (m.isPresent()) {
                    var a = findDiagonalDownRight(m.get(), 'A', coordinates);
                    if (a.isPresent()) {
                        var s = findDiagonalDownRight(a.get(), 'S', coordinates);
                        if (s.isPresent()) {
                            System.out.println("Found XMAS");
                            sumOfXmas++;

                            x.visited(true);
                            m.ifPresent(i -> i.visited(true));
                            a.ifPresent(i -> i.visited(true));
                            s.ifPresent(i -> i.visited(true));
                        }
                    }
                }
            }

            {

                Optional<Coordinate> m = findDiagonalDownLeft(x, 'M', coordinates);

                if (m.isPresent()) {
                    var a = findDiagonalDownLeft(m.get(), 'A', coordinates);
                    if (a.isPresent()) {
                        var s = findDiagonalDownLeft(a.get(), 'S', coordinates);
                        if (s.isPresent()) {
                            System.out.println("Found XMAS");
                            sumOfXmas++;

                            x.visited(true);
                            m.ifPresent(i -> i.visited(true));
                            a.ifPresent(i -> i.visited(true));
                            s.ifPresent(i -> i.visited(true));
                        }
                    }
                }
            }

            {

                Optional<Coordinate> m = findDiagonalUpRight(x, 'M', coordinates);

                if (m.isPresent()) {
                    var a = findDiagonalUpRight(m.get(), 'A', coordinates);
                    if (a.isPresent()) {
                        var s = findDiagonalUpRight(a.get(), 'S', coordinates);
                        if (s.isPresent()) {
                            System.out.println("Found XMAS");
                            sumOfXmas++;

                            x.visited(true);
                            m.ifPresent(i -> i.visited(true));
                            a.ifPresent(i -> i.visited(true));
                            s.ifPresent(i -> i.visited(true));
                        }
                    }
                }
            }

            {
                Optional<Coordinate> m = findDiagonalUpLeft(x, 'M', coordinates);

                if (m.isPresent()) {
                    var a = findDiagonalUpLeft(m.get(), 'A', coordinates);
                    if (a.isPresent()) {
                        var s = findDiagonalUpLeft(a.get(), 'S', coordinates);
                        if (s.isPresent()) {
                            System.out.println("Found XMAS");
                            sumOfXmas++;

                            x.visited(true);
                            m.ifPresent(i -> i.visited(true));
                            a.ifPresent(i -> i.visited(true));
                            s.ifPresent(i -> i.visited(true));
                        }
                    }
                }

            }


        }


        printCoordinates(coordinates);


        assertEquals(2532, sumOfXmas);
    }

    Optional<Coordinate> findLeft(Coordinate coordinate, char c, List<Coordinate> coordinates) {
        return coordinates.stream()
                .filter(i -> i.x == coordinate.x - 1 && i.y == coordinate.y)
                .filter(i -> i.c == c)
                .findFirst();
    }

    Optional<Coordinate> findRight(Coordinate coordinate, char c, List<Coordinate> coordinates) {
        return coordinates.stream()
                .filter(i -> i.x == coordinate.x + 1 && i.y == coordinate.y)
                .filter(i -> i.c == c)
                .findFirst();
    }

    Optional<Coordinate> findUp(Coordinate coordinate, char c, List<Coordinate> coordinates) {
        return coordinates.stream()
                .filter(i -> i.x == coordinate.x && i.y == coordinate.y - 1)
                .filter(i -> i.c == c)
                .findFirst();
    }

    Optional<Coordinate> findDown(Coordinate coordinate, char c, List<Coordinate> coordinates) {
        return coordinates.stream()
                .filter(i -> i.x == coordinate.x && i.y == coordinate.y + 1)
                .filter(i -> i.c == c)
                .findFirst();
    }

    Optional<Coordinate> findDiagonalDownRight(Coordinate coordinate, char c, List<Coordinate> coordinates) {
        return coordinates.stream()
                .filter(i -> i.x == coordinate.x + 1 && i.y == coordinate.y + 1)
                .filter(i -> i.c == c)
                .findFirst();
    }

    Optional<Coordinate> findDiagonalDownLeft(Coordinate coordinate, char c, List<Coordinate> coordinates) {
        return coordinates.stream()
                .filter(i -> i.x == coordinate.x - 1 && i.y == coordinate.y + 1)
                .filter(i -> i.c == c)
                .findFirst();
    }

    Optional<Coordinate> findDiagonalUpRight(Coordinate coordinate, char c, List<Coordinate> coordinates) {
        return coordinates.stream()
                .filter(i -> i.x == coordinate.x + 1 && i.y == coordinate.y - 1)
                .filter(i -> i.c == c)
                .findFirst();
    }

    Optional<Coordinate> findDiagonalUpLeft(Coordinate coordinate, char c, List<Coordinate> coordinates) {
        return coordinates.stream()
                .filter(i -> i.x == coordinate.x - 1 && i.y == coordinate.y - 1)
                .filter(i -> i.c == c)
                .findFirst();
    }

    Optional<Coordinate> findDiagonalUpLeft(Coordinate coordinate, List<Coordinate> coordinates) {
        return coordinates.stream()
                .filter(i -> i.x == coordinate.x - 1 && i.y == coordinate.y - 1)
                .findFirst();
    }

    Optional<Coordinate> findDiagonalUpRight(Coordinate coordinate, List<Coordinate> coordinates) {
        return coordinates.stream()
                .filter(i -> i.x == coordinate.x + 1 && i.y == coordinate.y - 1)
                .findFirst();
    }

    Optional<Coordinate> findDiagonalDownRight(Coordinate coordinate, List<Coordinate> coordinates) {
        return coordinates.stream()
                .filter(i -> i.x == coordinate.x + 1 && i.y == coordinate.y + 1)
                .findFirst();
    }

    Optional<Coordinate> findDiagonalDownLeft(Coordinate coordinate, List<Coordinate> coordinates) {
        return coordinates.stream()
                .filter(i -> i.x == coordinate.x - 1 && i.y == coordinate.y + 1)
                .findFirst();
    }


    private void printCoordinates(List<Coordinate> coordinates) {
        for (Coordinate c : coordinates) {
            if (c.x() == 0) {
                System.out.println();
            }

            if (!c.visited()) {
                System.out.print(".");
            } else {
                System.out.print(c.c());
            }
        }
    }


    List<Coordinate> findNeighbours(Coordinate coordinate, List<Coordinate> coordinates, List<Coordinate> exclude) {
        List<Coordinate> neighbours = new ArrayList<>();

        for (Coordinate c : coordinates) {
            if (c.x() == coordinate.x() && c.y() == coordinate.y() + 1) {
                neighbours.add(c);
            }

            if (c.x() == coordinate.x() && c.y() == coordinate.y() - 1) {
                neighbours.add(c);
            }

            if (c.x() == coordinate.x() + 1 && c.y() == coordinate.y()) {
                neighbours.add(c);
            }

            if (c.x() == coordinate.x() - 1 && c.y() == coordinate.y()) {
                neighbours.add(c);
            }

            if (c.x() == coordinate.x() + 1 && c.y() == coordinate.y() + 1) {
                neighbours.add(c);
            }

            if (c.x() == coordinate.x() - 1 && c.y() == coordinate.y() - 1) {
                neighbours.add(c);
            }

            if (c.x() == coordinate.x() + 1 && c.y() == coordinate.y() - 1) {
                neighbours.add(c);
            }

            if (c.x() == coordinate.x() - 1 && c.y() == coordinate.y() + 1) {
                neighbours.add(c);
            }

        }

        neighbours.removeAll(exclude);

        return neighbours;
    }


    static class Coordinate {
        private final int x;
        private final int y;
        private final char c;
        private boolean visited = false;

        Coordinate(int x, int y, char c) {
            this.x = x;
            this.y = y;
            this.c = c;
        }

        public Coordinate of(int x, int y, char c) {
            return new Coordinate(x, y, c);
        }

        public int x() {
            return x;
        }

        public int y() {
            return y;
        }

        public char c() {
            return c;
        }

        public boolean visited() {
            return visited;
        }

        public void visited(boolean visited) {
            this.visited = visited;
        }

        @Override
        public String toString() {
            return "Coordinate[" +
                    "x=" + x + ", " +
                    "y=" + y + ", " +
                    "c=" + c + ']';
        }
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

    @Test
    void day4_part2() throws IOException, URISyntaxException {
        var content = Files.readAllLines(Path.of(Objects.requireNonNull(this.getClass().getResource("/day4/part1_input.txt")).toURI()));

                /*
        String content = """
                        .M.S......
                        ..A..MSMS.
                        .M.S.MAA..
                        ..A.ASMSM.
                        .M.S.M....
                        ..........
                        S.S.S.S.S.
                        .A.A.A.A..
                        M.M.M.M.M.
                        ..........
                        """;
        */

        List<Coordinate> coordinates = new ArrayList<>();

        for (int i = 0; i < content.size(); i++) {
            for (int j = 0; j < content.get(i).length(); j++) {
                char c = content.get(i).charAt(j);

                coordinates.add(new Coordinate(j, i, c));

            }
        }

        int sumOfXmas = coordinates.stream()
                .filter(i -> i.c == 'A')
                .map(a -> {
                    Optional<Coordinate> x = findDiagonalUpLeft(a, coordinates);
                    Optional<Coordinate> y = findDiagonalUpRight(a, coordinates);
                    Optional<Coordinate> z = findDiagonalDownLeft(a, coordinates);
                    Optional<Coordinate> d = findDiagonalDownRight(a, coordinates);

                    if (x.isPresent() && y.isPresent() && z.isPresent() && d.isPresent()) {
                        var list = Stream.of(x.get(), y.get(), z.get(), d.get())
                                .map(i -> String.valueOf(i.c))
                                .collect(Collectors.joining());

                        if (list.equals("MMSS") || list.equals("SSMM") || list.equals("SMSM") || list.equals("MSMS")) {

                            x.ifPresent(i -> i.visited(true));
                            y.ifPresent(i -> i.visited(true));
                            z.ifPresent(i -> i.visited(true));
                            d.ifPresent(i -> i.visited(true));
                            a.visited(true);

                            return 1;
                        }

                        return 0;
                    }
                    return 0;
                })
                .reduce(0, Integer::sum);


        printCoordinates(coordinates);


        assertEquals(1941, sumOfXmas);

    }
}