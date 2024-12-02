package com.github.bardyr;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Utils {


    public static String getInput(String name) throws IOException {
        String input;
        try (InputStream resourceAsStream = Utils.class.getResourceAsStream(name)) {
            if (resourceAsStream == null) {
                throw new IllegalArgumentException("File not found!");
            }

            input = new String(resourceAsStream.readAllBytes(), StandardCharsets.UTF_8);
        }

        return input;
    }

    public static int[] convertListToInt(String[] list) {
        return Arrays.stream(list).mapToInt(Integer::parseInt).toArray();
    }

    public static List<Integer> convertListToInteger(String[] list) {
        return Arrays.stream(list).mapToInt(Integer::parseInt).boxed().toList();
    }

    public static Path getPath(String name) {
        try {
            return Path.of(Objects.requireNonNull(Utils.class.getResource(name)).toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static Stream<String> getLines (String name)  {
        try {
            return Files.readAllLines(getPath(name), StandardCharsets.UTF_8).stream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
