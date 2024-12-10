package com.github.bardyr;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day7Test {

    @Test
    void part1() throws IOException, URISyntaxException {
        var content = Files.readAllLines(Path.of(Objects.requireNonNull(this.getClass().getResource("/day7/input.txt")).toURI()));

        Long list = content.stream()
                .parallel()
                .map(Callibration::of)
                .mapToLong(Callibration::equations)
                .sum();

        assertEquals(8401132154762L, list);

    }

    @Test
    void part2() throws IOException, URISyntaxException {
        var content = Files.readAllLines(Path.of(Objects.requireNonNull(this.getClass().getResource("/day7/input.txt")).toURI()));

        Long list = content.stream()
                .parallel()
                .map(Callibration::ofBase3)
                .mapToLong(Callibration::equations)
                .sum();

        assertEquals(95297119227552L, list);

    }

    static class Callibration {

        Long sum;
        List<String> parts;
        List<String> operations = List.of("*", "+");

        public Callibration(Long sum, List<String> parts, List<String> operations) {
            this.sum = sum;
            this.parts = parts;
            this.operations = operations;
        }

        public static Callibration of(String s) {

            String[] split = s.split(":");

            Long sum = Long.parseLong(split[0].trim());
            List<String> parts = Arrays.stream(split[1].trim().split(" "))
                    .toList();
            return new Callibration(sum, parts);
        }

        public static Callibration ofBase3(String s) {

            String[] split = s.split(":");

            Long sum = Long.parseLong(split[0].trim());
            List<String> parts = Arrays.stream(split[1].trim().split(" "))
                    .toList();
            return new Callibration(sum, parts, List.of("*", "+", "||"));
        }

        Callibration(Long sum, List<String> parts) {
            this.sum = sum;
            this.parts = parts;
        }


        // expand parts and operations
        public Long equations() {
            int combinations = (int) Math.pow(operations.size(), parts.size() - 1);

            for (int i = 0; i < combinations; i++) {
                String binary = StringUtils.leftPad(Integer.toUnsignedString(i, operations.size()), parts.size() - 1, "0");

                Long count = Long.parseLong(parts.get(0));

                for (int j = 0; j < binary.length(); j++) {
                    char c = binary.charAt(j);

                    if (c == '0') {
                        count *= Long.parseLong(parts.get(j + 1));
                    }
                    if (c == '1') {
                        count += Long.parseLong(parts.get(j + 1));
                    }

                    if (c == '2') {
                        count = Long.parseLong(count + parts.get(j + 1));
                    }
                }

                if (count.equals(sum)) {
                    return count;
                }
            }

            return 0L;
        }

        @Override
        public String toString() {
            return "Callibration{" +
                    "sum=" + sum +
                    ", parts=" + parts +
                    '}';
        }
    }
}