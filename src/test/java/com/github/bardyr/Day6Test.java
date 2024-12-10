package com.github.bardyr;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("ALL")
class Day6Test {

    @Test
    void day6_part1() throws IOException, URISyntaxException {
        var content = Files.readAllLines(Path.of(Objects.requireNonNull(this.getClass().getResource("/day6/part1_input.txt")).toURI()));

        List<Coordinate> coordinates = new ArrayList<>();

        for (int i = 0; i < content.size(); i++) {
            for (int j = 0; j < content.get(i).length(); j++) {
                char c = content.get(i).charAt(j);
                coordinates.add(new Coordinate(j, i, c));
            }
        }

        normalizeCoordinates(coordinates);

        long count = coordinates.stream()
                .filter(Coordinate::isGuard)
                .findFirst()
                .map(this::goWalk)
                .stream()
                .flatMap(List::stream)
                .map(Pair::getLeft)
                .distinct()
                .count()                ;

        assertEquals(5453, count);
    }

    private void normalizeCoordinates(List<Coordinate> coordinates) {

        coordinates.forEach(coordinate -> {
            coordinate.up = coordinates.stream()
                    .filter(c -> c.x == coordinate.x && c.y == coordinate.y - 1)
                    .findFirst();

            coordinate.down = coordinates.stream()
                    .filter(c -> c.x == coordinate.x && c.y == coordinate.y + 1)
                    .findFirst();
            coordinate.left = coordinates.stream()
                    .filter(c -> c.x == coordinate.x - 1 && c.y == coordinate.y)
                    .findFirst();

            coordinate.right = coordinates.stream()
                    .filter(c -> c.x == coordinate.x + 1 && c.y == coordinate.y)
                    .findFirst();
        });
    }

    @Test
    void day6_part2() throws IOException, URISyntaxException {
        var content = Files.readAllLines(Path.of(Objects.requireNonNull(this.getClass().getResource("/day6/part1_input.txt")).toURI()));

        List<Coordinate> coordinates = new ArrayList<>();

        for (int i = 0; i < content.size(); i++) {
            for (int j = 0; j < content.get(i).length(); j++) {
                char c = content.get(i).charAt(j);
                coordinates.add(new Coordinate(j, i, c));
            }
        }

        normalizeCoordinates(coordinates);

        Optional<Coordinate> guard = coordinates.stream()
                .filter(Coordinate::isGuard)
                .findFirst();

        Optional<Long> l = guard.map(g -> {
            System.out.println("Guard found at: " + g.x + " " + g.y);

            List<Coordinate> pairs = goWalk(g).stream()
                    .map(Pair::getLeft)
                    .distinct()
                    .toList();

            return pairs.stream()
                    .map(coordinate -> {

                        char c = coordinate.c;
                        coordinate.c = '#';
                        boolean looped = goWalkLoop(g, Direction.UP);
                        coordinate.c = c;

                        return looped;
                    })
                    .filter(c -> c)
                    .count();
        });

        assertEquals(2188, l.orElseThrow());
    }

    private List<Pair<Coordinate, Direction>> goWalk(Coordinate startCoordinate) {
        List<Pair<Coordinate, Direction>> history = new ArrayList<>();
        Optional<Coordinate> walk = Optional.of(startCoordinate);
        Direction direction = Direction.UP;
        history.add(Pair.of(walk.get(), direction));

        while (true) {
            walk = walk.get().walk(direction);

            if (walk.isEmpty()) {
                break;
            }

            if (history.contains(Pair.of(walk.get(), direction))) {
                break;
            }


            history.add(Pair.of(walk.get(), direction));

            if (walk.get().isNextBlocked(direction)) {
                direction = rotate(direction);
            }
        }

        return history;
    }

    private boolean goWalkLoop(Coordinate startCoordinate, Direction direction) {
        Set<Pair<Coordinate, Direction>> history = new HashSet<>();
        Optional<Coordinate> walk = Optional.of(startCoordinate);

        history.add(Pair.of(walk.get(), direction));

        while (true) {
            walk = walk.get().walk(direction);

            if (walk.isEmpty()) {
                return false;
            }

            if (history.contains(Pair.of(walk.get(), direction))) {
                return true;
            }

            history.add(Pair.of(walk.get(), direction));

            if (walk.get().isNextBlocked(direction)) {
                direction = rotate(direction);

                Optional<Boolean> b = walk.get().walk(direction).map(Coordinate::isBlocked);

                if (b.isPresent() && b.get()) {
                    direction = rotate(direction);
                }
            }
        }
    }

    private Direction rotate(Direction direction) {
        return switch (direction) {
            case UP -> Direction.RIGHT;
            case RIGHT -> Direction.DOWN;
            case DOWN -> Direction.LEFT;
            case LEFT -> Direction.UP;
        };
    }

    enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private static class Coordinate {
        int x;
        int y;
        char c;

        Optional<Coordinate> up;
        Optional<Coordinate> down;
        Optional<Coordinate> left;
        Optional<Coordinate> right;

        public Coordinate(int x, int y, char c) {
            this.x = x;
            this.y = y;
            this.c = c;
        }

        boolean isGuard() {
            return c == '^';
        }

        public Optional<Coordinate> walk(Direction direction) {
            return switch (direction) {
                case UP -> this.up;
                case DOWN -> this.down;
                case LEFT -> this.left;
                case RIGHT -> this.right;
            };
        }

        public String toString() {
            return "Coordinate{" + "x=" + x + ", y=" + y + ", c=" + c + '}';
        }

        public boolean isBlocked() {
            return c == '#';
        }

        public boolean isNextBlocked(Direction direction) {
            return walk(direction)
                    .map(Coordinate::isBlocked)
                    .orElse(false);
        }
    }
}