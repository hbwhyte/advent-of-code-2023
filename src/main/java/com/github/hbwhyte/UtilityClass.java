package com.github.hbwhyte;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class UtilityClass {

    public static List<String> loadInput(final String fileName) {
        try (InputStream input = Day01_Trebuchet.class.getClassLoader().getResourceAsStream(fileName)) {
            return new BufferedReader(new InputStreamReader(requireNonNull(input))).lines().toList();
        } catch (IOException exc) {
            System.out.print("Failed to read file. " + exc);
            return Collections.emptyList();
        }
    }
}
