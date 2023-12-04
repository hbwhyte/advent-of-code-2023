package com.github.hbwhyte;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Arrays;
import java.util.List;

import static com.github.hbwhyte.UtilityClass.loadInput;

/**
 * https://adventofcode.com/2023/day/4
 */
@Slf4j
public class Day04_Scratchcards {

    public static void main(String[] args) {
        final Integer answer1 = part1("day04.txt");
        log.info("The scratchcards are worth {} points", answer1);
    }

    public static Integer part1(String fileName) {
        List<String> lines = loadInput(fileName);

        return lines.stream()
            .map(Day04_Scratchcards::checkScratchcard)
            .mapToInt(Integer::intValue)
            .sum();
    }

    private static Integer checkScratchcard(String line) {
        String[] splitLine = line.split(": |\\| ");
        List<Integer> winningNumbers = Arrays.stream(splitLine[1].split(" "))
            .filter(s -> !s.isEmpty())
            .map(Integer::parseInt)
            .toList();
        List<Integer> yourNumbers = Arrays.stream(splitLine[2].split(" "))
            .filter(s -> !s.isEmpty())
            .map(Integer::parseInt)
            .toList();
        final int yourWinningNumbers = CollectionUtils.intersection(winningNumbers, yourNumbers).size();
        log.info("Your winning numbers: {}", yourWinningNumbers);
        if (yourWinningNumbers == 0) {
            log.info("No points");
            return 0;
        } else {
            final Integer result = (int) Math.pow(2, yourWinningNumbers - 1);
            log.info("Points worth {}", result);
            return result;
        }
    }
}
