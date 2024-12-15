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

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day10Test {

    @Test
    void part1() throws URISyntaxException, IOException {

        List<Coordinate> coordinates = getCoordinates("/day10/input.txt");

        List<Coordinate> startingPoints = coordinates.stream()
                .filter(Coordinate::isStartingPoint)
                .toList();

        long sum = startingPoints.stream()
                .map(coordinate -> findAllPaths(coordinate, 9))
                .map(this::score)
                .mapToLong(Long::longValue)
                .sum();

        assertEquals(468, sum);
    }

    @Test
    void part2() throws URISyntaxException, IOException {

        List<Coordinate> coordinates = getCoordinates("/day10/input.txt");

        List<Coordinate> startingPoints = coordinates.stream()
                .filter(Coordinate::isStartingPoint)
                .toList();

        long sum = startingPoints.stream()
                .map(coordinate -> findAllPaths(coordinate, 9))
                .map(this::scorePart2)
                .mapToLong(Long::longValue)
                .sum();

        assertEquals(966, sum);
    }

    private List<Coordinate> getCoordinates(String file) throws IOException, URISyntaxException {
        List<String> content = Files.readAllLines(Path.of(Objects.requireNonNull(this.getClass().getResource(file)).toURI()));

        List<Coordinate> coordinates = new ArrayList<>();

        for (int i = 0; i < content.size(); i++) {
            for (int j = 0; j < content.get(i).length(); j++) {
                coordinates.add(new Coordinate(j, i, Integer.parseInt(String.valueOf(content.get(i).charAt(j)))));
            }
        }

        normalizeCoordinates(coordinates);
        return coordinates;
    }

    Long scorePart2(List<List<Coordinate>> paths) {
        return (long) paths.size();
    }

    Long score(List<List<Coordinate>> paths) {
        return paths.stream()
                .flatMap(List::stream)
                .filter(c -> c.value == 9)
                .distinct()
                .count();
    }

    private void normalizeCoordinates(List<Coordinate> coordinates) {

        coordinates.forEach(coordinate -> {
            coordinate.setUp(coordinates.stream()
                    .filter(c -> c.x == coordinate.x && c.y == coordinate.y - 1)
                    .findFirst());

            coordinate.setDown(coordinates.stream()
                    .filter(c -> c.x == coordinate.x && c.y == coordinate.y + 1)
                    .findFirst());

            coordinate.setLeft(coordinates.stream()
                    .filter(c -> c.x == coordinate.x - 1 && c.y == coordinate.y)
                    .findFirst());

            coordinate.setRight(coordinates.stream()
                    .filter(c -> c.x == coordinate.x + 1 && c.y == coordinate.y)
                    .findFirst());
        });
    }

    private List<List<Coordinate>> findAllPaths(Coordinate start, int targetValue) {
        List<List<Coordinate>> paths = new ArrayList<>();
        findAllPathsRecursive(start, targetValue, new ArrayList<>(), new ArrayList<>(), paths);
        return paths;
    }

    private void findAllPathsRecursive(Coordinate current,
                                       int targetValue,
                                       List<Coordinate> currentPath,
                                       List<Coordinate> visited,
                                       List<List<Coordinate>> paths) {
        currentPath.add(current);
        visited.add(current);

        if (current.value == targetValue) {
            paths.add(new ArrayList<>(currentPath));
        } else {

            List<Coordinate> surroundingTrail = current.getSurroundingTrail();
            surroundingTrail.removeAll(visited);

            for (Coordinate neighbor : surroundingTrail) {
                visited.add(neighbor);
                findAllPathsRecursive(neighbor, targetValue, new ArrayList<>(currentPath), new ArrayList<>(visited), paths);
            }

        }
    }

    private static class Coordinate {
        private final int x;
        private final int y;
        private final int value;

        private Optional<Coordinate> up;
        private Optional<Coordinate> down;
        private Optional<Coordinate> left;
        private Optional<Coordinate> right;

        public Coordinate(int x, int y, int value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }

        boolean isStartingPoint() {
            return value == 0;
        }

        public String toString() {
            return "C{" + x +
                    "," + y +
                    ",v=" + value +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Coordinate that)) return false;
            return x == that.x && y == that.y && value == that.value;
        }

        public void setUp(Optional<Coordinate> up) {
            this.up = up;
        }

        public void setDown(Optional<Coordinate> down) {
            this.down = down;
        }

        public void setLeft(Optional<Coordinate> left) {
            this.left = left;
        }

        public void setRight(Optional<Coordinate> right) {
            this.right = right;
        }

        public List<Coordinate> getSurroundingTrail() {
            List<Coordinate> coordinates = new ArrayList<>();
            List.of(up, down, left, right)
                    .forEach(coordinate -> coordinate.ifPresent(c -> {

                        if (c.value == 0) {
                            return;
                        }

                        if (c.value == this.value + 1) {
                            coordinates.add(c);
                        }
                    }));

            return coordinates;
        }
    }
}
