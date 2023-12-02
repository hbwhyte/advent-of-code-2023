package com.github.hbwhyte;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.github.hbwhyte.Day02_CubeConundrum.Colour.*;
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
        final Integer answer1 = part1("day02.txt");
        log.info("The sum of the idea of the games is {}", answer1);
        final Integer answer2 = part2("day02.txt");
        log.info("The sum of the power of the games is {}", answer2);
    }

    public static Integer part1(final String fileName) {
        List<String> lines = loadInput(fileName);
        return lines.stream()
                .map(Day02_CubeConundrum::evaluateGame)
                .mapToInt(Integer::intValue)
                .sum();
    }

    public static Integer part2(final String fileName) {
        List<String> lines = loadInput(fileName);
        return lines.stream()
                .map(Day02_CubeConundrum::powerOfTheGame)
                .mapToInt(Integer::intValue)
                .sum();
    }

    private static Integer powerOfTheGame(final String game) {
        // Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
        String sets = game.split(": ")[1];

        Map<Colour, Integer> mins = Colour.getMins();
        Arrays.stream(sets.split("; |, ")).forEach(cubes -> getMins(cubes, mins));
        return getPower(mins);
    }

    private static void getMins(final String cubes, final Map<Colour, Integer> mins) {
        final String[] c = cubes.split(" ");
        final int number = Integer.parseInt(c[0]);
        final Colour colour = Colour.valueOf(c[1].toUpperCase());
        switch (colour) {
            case RED -> {
                if (number > mins.get(RED)) {
                    mins.put(RED, number);
                }
            }
            case GREEN -> {
                if (number > mins.get(GREEN)) {
                    mins.put(GREEN, number);
                }
            }
            case BLUE -> {
                if (number > mins.get(BLUE)) {
                    mins.put(BLUE, number);
                }
            }
        }
    }

    public enum Colour {
        RED,
        GREEN,
        BLUE;

        public static Map<Colour, Integer> getMins() {
            Map<Colour, Integer> mins = new HashMap<>();
            mins.put(RED, 0);
            mins.put(GREEN, 0);
            mins.put(BLUE, 0);
            return mins;
        }

        public static Integer getPower(final Map<Colour, Integer> colours) {
            return colours.get(RED) * colours.get(GREEN) * colours.get(BLUE);
        }

    }


    // PART 1 //
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
