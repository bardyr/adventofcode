package com.github.bardyr;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day5Test {

    record Rule(int page, int before) {
        static Rule of(String s) {
            String[] split = s.split("\\|");
            return new Rule(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
        }
    }

    record Updates(List<Integer> pages) {
        static Updates of(String s) {
            return new Updates(Arrays.stream(s.split(","))
                    .map(Integer::parseInt)
                    .toList());
        }

        boolean validate(List<Rule> rules) {
            for (Rule rule : rules) {
                int page = pages.indexOf(rule.page());
                int before = pages.indexOf(rule.before());

                if (page != -1 && before != -1 && page > before) {
                    return false;
                }
            }
            return true;
        }

        Updates fixOrder(List<Rule> rules) {
            List<Integer> newPages = new ArrayList<>(pages);
            for (Rule rule : rules) {
                int page = newPages.indexOf(rule.page());
                int before = newPages.indexOf(rule.before());

                if (page != -1 && before != -1 && page > before) {
                    newPages.remove(page);
                    newPages.add(before, rule.page());
                }
            }
            return new Updates(newPages);
        }

        Updates fixOrderAll(List<Rule> rules) {
            Updates update = this;

            while (!update.validate(rules)) {
                update = update.fixOrder(rules);
            }

            return update;
        }

        int getMiddlePage() {

            return pages.get((int) Math.ceil((double) pages.size() / 2) - 1);
        }
    }


    @Test
    void day5_part1() throws IOException, URISyntaxException {
        var content = Files.readAllLines(Path.of(Objects.requireNonNull(this.getClass().getResource("/day5/part1_input.txt")).toURI()));

        List<Rule> rules = new ArrayList<>();
        List<Updates> updates = new ArrayList<>();

        for (String line : content) {
            if (line.contains("|")) {
                rules.add(Rule.of(line));
            }

            if (line.contains(",")) {
                updates.add(Updates.of(line));
            }
        }

        Integer reduce = updates.stream()
                .filter(u -> u.validate(rules))
                .map(Updates::getMiddlePage)
                .reduce(0, Integer::sum);


        assertEquals(6505, reduce);
    }

    @Test
    void day5_part2() throws IOException, URISyntaxException {
        var content = Files.readAllLines(Path.of(Objects.requireNonNull(this.getClass().getResource("/day5/part1_input.txt")).toURI()));

        List<Rule> rules = new ArrayList<>();
        List<Updates> updates = new ArrayList<>();

        for (String line : content) {
            if (line.contains("|")) {
                rules.add(Rule.of(line));
            }

            if (line.contains(",")) {
                updates.add(Updates.of(line));
            }
        }

        Integer reduce = updates.stream()
                .filter(u -> !u.validate(rules))
                .map(u -> u.fixOrderAll(rules))
                .map(Updates::getMiddlePage)
                .reduce(0, Integer::sum);

        assertEquals(6897, reduce);
    }
}