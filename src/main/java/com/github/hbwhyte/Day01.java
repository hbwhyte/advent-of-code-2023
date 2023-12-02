package com.github.hbwhyte;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Character.isDigit;
import static java.util.Objects.requireNonNull;

/**
 * --- Day 1: Trebuchet?! ---
 * Something is wrong with global snow production, and you've been selected to take a look. The Elves have even given
 * you a map; on it, they've used stars to mark the top fifty locations that are likely to be having problems.
 * <br>
 * You've been doing this long enough to know that to restore snow operations, you need to check all fifty stars by
 * December 25th.
 * <br>
 * Collect stars by solving puzzles. Two puzzles will be made available on each day in the Advent calendar; the second
 * puzzle is unlocked when you complete the first. Each puzzle grants one star. Good luck!
 * <br>
 * You try to ask why they can't just use a weather machine ("not powerful enough") and where they're even sending you
 * ("the sky") and why your map looks mostly blank ("you sure ask a lot of questions") and hang on did you just say the
 * sky ("of course, where do you think snow comes from") when you realize that the Elves are already loading you into a
 * trebuchet ("please hold still, we need to strap you in").
 * <br>
 * As they're making the final adjustments, they discover that their calibration document (your puzzle input) has been
 * amended by a very young Elf who was apparently just excited to show off her art skills. Consequently, the Elves are
 * having trouble reading the values on the document.
 * <br>
 * The newly-improved calibration document consists of lines of text; each line originally contained a specific
 * calibration value that the Elves now need to recover. On each line, the calibration value can be found by combining
 * the first digit and the last digit (in that order) to form a single two-digit number.
 * <br>
 * For example:
 * <br>
 * 1abc2
 * pqr3stu8vwx
 * a1b2c3d4e5f
 * treb7uchet
 * <br>
 * In this example, the calibration values of these four lines are 12, 38, 15, and 77. Adding these together produces 142.
 * <br>
 * Consider your entire calibration document. What is the sum of all the calibration values?
 */
public class Day01 {

    public static void main(String[] args) {
        System.out.println("Day 01: " + calibrateTrebuchet("day01.txt"));
    }

    public static Integer calibrateTrebuchet(final String fileName) {
        List<String> calibrationLines = loadInput(fileName);
        return calibrationLines.stream()
                .map(Day01::part2)
                .mapToInt(Integer::intValue)
                .sum();
    }

    private static Integer part1(final String line) {
        Character firstDigit = '0';
        Character lastDigit = '0';
        boolean firstDigitFound = false;
        for (Character c : line.toCharArray()) {
            if (isDigit(c) && !firstDigitFound) {
                firstDigit = c;
                lastDigit = c;
                firstDigitFound = true;
            } else if (isDigit(c)) {
                lastDigit = c;
            }
        }
        System.out.printf("Line is %s -> First digit is %s and second digit is %s\n", line, firstDigit, lastDigit);
        final String calibrationValue = firstDigit.toString() + lastDigit.toString();
        return Integer.valueOf(calibrationValue);
    }

    private static Integer part2(final String line) {
        final String regex = "([0-9]|one|two|three|four|five|six|seven|eight|nine)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        String first = "";
        String last = "";

        if (matcher.find()) {
            first = matcher.group();
            do {
                last = matcher.group();
            } while (matcher.find(matcher.start() + 1));
        }

        final String calibrationValue = stringToInt(first) + stringToInt(last);
        return Integer.valueOf(calibrationValue);
    }

    private static String stringToInt(final String number) {
        return switch (number) {
            case "one" -> "1";
            case "two" -> "2";
            case "three" -> "3";
            case "four" -> "4";
            case "five" -> "5";
            case "six" -> "6";
            case "seven" -> "7";
            case "eight" -> "8";
            case "nine" -> "9";
            default -> number;
        };
    }

    private static List<String> loadInput(final String fileName) {
        try (InputStream input = Day01.class.getClassLoader().getResourceAsStream(fileName)) {
            return new BufferedReader(new InputStreamReader(requireNonNull(input))).lines().toList();
        } catch (IOException exc) {
            System.out.print("Failed to read file. " + exc);
            return Collections.emptyList();
        }
    }
}
