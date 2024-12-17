package com.github.bardyr;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day12Test {

    @Test
    void part1() throws IOException, URISyntaxException {
        List<Coordinate> coordinates = getCoordinates("/day12/input.txt");

        List<Set<Coordinate>> allRegions = coordinates.stream()
                .map(c -> findRegion(c, new HashSet<>()))
                .distinct()
                .toList();

        Long regions = allRegions.stream()
                .map(Region::new)
                .mapToLong(Region::getCost)
                .sum();


        assertEquals(1483212, regions);

        printCoordinates(coordinates);

    }

    @Test
    void part2() throws IOException, URISyntaxException {
        List<Coordinate> coordinates = getCoordinates("/day12/input.txt");

        List<Set<Coordinate>> allRegions = coordinates.stream()
                .map(c -> findRegion(c, new HashSet<>()))
                .distinct()
                .toList();

        long bulkCost = allRegions.stream()
                .map(Region::new)
                .mapToLong(Region::getBulkCost)
                .sum();

        assertEquals(897062, bulkCost);
    }

    private static class Region {
        Set<Coordinate> region;

        private Region(Set<Coordinate> region) {
            this.region = region;

            for (Coordinate coordinate : region) {
                coordinate.setUpRight(region.stream()
                        .filter(c -> c.x == coordinate.x + 1 && c.y == coordinate.y - 1)
                        .findFirst());

                coordinate.setUpLeft(region.stream()
                        .filter(c -> c.x == coordinate.x - 1 && c.y == coordinate.y - 1)
                        .findFirst());

                coordinate.setDownRight(region.stream()
                        .filter(c -> c.x == coordinate.x + 1 && c.y == coordinate.y + 1)
                        .findFirst());

                coordinate.setDownLeft(region.stream()
                        .filter(c -> c.x == coordinate.x - 1 && c.y == coordinate.y + 1)
                        .findFirst());
            }
        }

        int getArea() {
            return region.size();
        }

        Long getPerimeter() {
            return region.stream()
                    .mapToLong(c -> {
                        List<Coordinate> coordinates = new ArrayList<>();
                        List<Optional<Coordinate>> neigbors = List.of(c.up, c.down, c.left, c.right);
                        neigbors
                                .forEach(coordinate -> coordinate.ifPresent(c1 -> {

                                    if (c1.value != c.value) {
                                        coordinates.add(c1);
                                    }
                                }));

                        long count = 0L;
                        if (c.up.isEmpty()) {
                            count++;
                        }

                        if (c.down.isEmpty()) {
                            count++;
                        }

                        if (c.left.isEmpty()) {
                            count++;
                        }

                        if (c.right.isEmpty()) {
                            count++;
                        }

                        return coordinates.size() + count;
                    })
                    .sum();
        }

        Long getCost() {
            return getArea() * getPerimeter();
        }

        public long getBulkCost() {
            return getArea() * getNumberOfSides();
        }

        private long getNumberOfSides() {
            return region.stream()
                    .map(Coordinate::Corners)
                    .mapToLong(Long::longValue)
                    .sum();
        }


    }

    private Set<Coordinate> findRegion(Coordinate current, Set<Coordinate> region) {

        region.add(current);

        List<Coordinate> surrounding = current.getSameNeighbors();
        surrounding.removeAll(region);

        for (Coordinate coordinate : surrounding) {
            region.addAll(findRegion(coordinate, region));
        }

        return region;

    }


    void printCoordinates(List<Coordinate> coordinates) {
        for (Coordinate coordinate : coordinates) {

            if (coordinate.x == 0) {
                System.out.println();
            }

            System.out.print(coordinate.value);

        }
        System.out.println();
    }

    private List<Coordinate> getCoordinates(String file) throws IOException, URISyntaxException {
        List<String> content = Files.readAllLines(Path.of(Objects.requireNonNull(this.getClass().getResource(file)).toURI()));

        List<Coordinate> coordinates = new ArrayList<>();

        for (int i = 0; i < content.size(); i++) {
            for (int j = 0; j < content.get(i).length(); j++) {
                coordinates.add(new Coordinate(j, i, content.get(i).charAt(j)));
            }
        }

        normalizeCoordinates(coordinates);
        return coordinates;
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

    private static class Coordinate {
        private final int x;
        private final int y;
        private final char value;

        private Optional<Coordinate> up = Optional.empty();
        private Optional<Coordinate> down = Optional.empty();
        private Optional<Coordinate> left = Optional.empty();
        private Optional<Coordinate> right = Optional.empty();

        private Optional<Coordinate> upRight = Optional.empty();
        private Optional<Coordinate> upLeft = Optional.empty();
        private Optional<Coordinate> downRight = Optional.empty();
        private Optional<Coordinate> downLeft = Optional.empty();


        public Coordinate(int x, int y, char value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }

        long Corners() {

            long corners = 0;

            boolean up = this.up.isPresent() && this.up.get().value == value;
            boolean down = this.down.isPresent() && this.down.get().value == value;
            boolean left = this.left.isPresent() && this.left.get().value == value;
            boolean right = this.right.isPresent() && this.right.get().value == value;
            boolean upRight = this.upRight.isPresent() && this.upRight.get().value == value;
            boolean upLeft = this.upLeft.isPresent() && this.upLeft.get().value == value;
            boolean downRight = this.downRight.isPresent() && this.downRight.get().value == value;
            boolean downLeft = this.downLeft.isPresent() && this.downLeft.get().value == value;

            if (!up && !right) {
                corners++;
            }

            if (!up && !left) {
                corners++;
            }

            if (!down && !right) {
                corners++;
            }

            if (!down && !left) {
                corners++;
            }

            //
            // check diagonals

            if (up && right) {
                if (!upRight) {
                    corners++;
                }
            }

            if (up && left) {
                if (!upLeft) {
                    corners++;
                }
            }

            if (down && right) {
                if (!downRight) {
                    corners++;
                }
            }

            if (down && left) {
                if (!downLeft) {
                    corners++;
                }
            }

            return corners;
        }

        List<Coordinate> getSameNeighbors() {
            List<Coordinate> coordinates = new ArrayList<>();
            List.of(up, down, left, right)
                    .forEach(coordinate -> coordinate.ifPresent(c -> {

                        if (c.value == this.value) {
                            coordinates.add(c);
                        }
                    }));

            return coordinates;
        }

        public String toString() {
            return "C{" + x +
                    "," + y +
                    ",v=" + value +
                    '}';
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

        public void setUpRight(Optional<Coordinate> upRight) {
            this.upRight = upRight;
        }

        public void setUpLeft(Optional<Coordinate> upLeft) {
            this.upLeft = upLeft;
        }

        public void setDownRight(Optional<Coordinate> downRight) {
            this.downRight = downRight;
        }

        public void setDownLeft(Optional<Coordinate> downLeft) {
            this.downLeft = downLeft;
        }
    }
}
