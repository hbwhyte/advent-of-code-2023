package com.github.hbwhyte;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.github.hbwhyte.UtilityClass.loadInput;

/**
 * https://adventofcode.com/2023/day/2
 */
@Slf4j
public class Day02_CubeConundrum {

    public static final Integer MAX_RED = 12;
    public static final Integer MAX_GREEN = 13;
    public static final Integer MAX_BLUE = 14;

    public static void main(String[] args) {
        final Integer answer = calculate("day02.txt");
        log.info("The sum of the idea of the games is {}", answer);
    }

    public static Integer calculate(final String fileName) {
        List<String> lines = loadInput(fileName);
        return lines.stream()
                .map(Day02_CubeConundrum::evaluateGame)
                .mapToInt(Integer::intValue)
                .sum();
    }

    private static Integer evaluateGame(final String game) {
        if (validGame(game)) {
            return gameValue(game);
        } else {
            return 0;
        }
    }

    private static boolean validGame(final String game) {
        // Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
        String sets = game.split(": ")[1];

        return Arrays.stream(sets.split("; |, "))
                .allMatch(Day02_CubeConundrum::valid);
    }

    private static boolean valid(final String cubes) {
        final String[] c = cubes.split(" ");
        final int number = Integer.parseInt(c[0]);
        final String colour = c[1].toLowerCase();
        return switch (colour) {
            case "red" -> number <= MAX_RED;
            case "green" -> number <= MAX_GREEN;
            case "blue" -> number <= MAX_BLUE;
            default -> false;
        };
    }

    private static Integer gameValue(final String game) {
        // Get first group of digits aka game number
        Pattern pattern = Pattern.compile("[\\d]*(\\d+)");
        Matcher matcher = pattern.matcher(game);
        String gameNumber = "0";
        if (matcher.find()) {
            gameNumber = matcher.group();
        }
        log.info("Game number {} is valid", gameNumber);
        return Integer.parseInt(gameNumber);
    }
}
