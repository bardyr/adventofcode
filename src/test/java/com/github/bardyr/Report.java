package com.github.bardyr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

record Report(List<Integer> levels) {

    static Report of(String levels) {
        return new Report(Arrays.stream(levels.split(" ")).map(Integer::parseInt).toList());
    }

    boolean isSafe() {
        int leaning = 0;

        for (int i = 0; i < levels.size() - 1; i++) {
            int diff = levels.get(i) - levels.get(i + 1);

            if (diff > 0 && (leaning == 0 || leaning == 1)) {
                leaning = 1;
            } else if (diff < 0 && (leaning == 0 || leaning == -1)) {
                leaning = -1;
            } else {
                return false;
            }

            if (Math.abs(diff) > 3) {
                return false;
            }
        }

        return true;
    }

    boolean isSafer() {
        for (int i = 0; i < levels.size(); i++) {
            List<Integer> newReport = new ArrayList<>();
            for (int j = 0; j < levels.size(); j++) {
                if (j != i) {
                    Integer integer = levels.get(j);
                    newReport.add(integer);
                }
            }
            Report report = new Report(newReport);
            if (report.isSafe()) {
                return true;
            }
        }
        return false;
    }
}
