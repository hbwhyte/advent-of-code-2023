package com.github.hbwhyte;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.hbwhyte.UtilityClass.loadInput;

/**
 * https://adventofcode.com/2023/day/4
 */
@Slf4j
public class Day04_Scratchcards {

    public static void main(String[] args) {
        final Integer answer1 = part1("day04.txt");
        log.info("The scratchcards are worth {} points", answer1);
        final Integer answer2 = part2("day04.txt");
        log.info("The number of scratchcards is {}", answer2);
    }

    public static Integer part1(String fileName) {
        List<String> lines = loadInput(fileName);

        return lines.stream()
            .map(Day04_Scratchcards::checkScratchcard)
            .mapToInt(Integer::intValue)
            .sum();
    }

    public static Integer part2(String fileName) {
        List<String> lines = loadInput(fileName);
        Map<Integer, Integer> totalCards = new HashMap<>();
        for (int i = 0; i < lines.size(); i++) {
            int gameNumber = i + 1;
            totalCards.merge(gameNumber, 1, Integer::sum);
            int yourWinningNumbers = yourWinningNumbers(lines.get(i));
            int extraCards = gameNumber + 1;
            while (yourWinningNumbers > 0) {
                totalCards.merge(extraCards, totalCards.get(gameNumber), Integer::sum);
                extraCards++;
                yourWinningNumbers--;
            }
        }
        log.info("Winning cards: {}", totalCards);

        return totalCards.values().stream()
            .mapToInt(Integer::intValue)
            .sum();
    }

    private static Integer yourWinningNumbers(final String line) {
        String[] splitLine = line.split(": |\\| ");
        List<Integer> winningNumbers = Arrays.stream(splitLine[1].split(" "))
            .filter(s -> !s.isEmpty())
            .map(Integer::parseInt)
            .toList();
        List<Integer> yourNumbers = Arrays.stream(splitLine[2].split(" "))
            .filter(s -> !s.isEmpty())
            .map(Integer::parseInt)
            .toList();
        return CollectionUtils.intersection(winningNumbers, yourNumbers).size();
    }

    private static Integer checkScratchcard(final String line) {
        final int yourWinningNumbers = yourWinningNumbers(line);
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
