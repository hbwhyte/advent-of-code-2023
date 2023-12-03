package com.github.hbwhyte;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.github.hbwhyte.UtilityClass.loadInput;

/**
 * https://adventofcode.com/2023/day/3
 */
@Slf4j
public class Day03_GearRatios {

    public static final String SYMBOL_REGEX = "[\\!\\\"\\#\\$\\%\\&\\'\\(\\)\\*\\+\\,\\-\\/\\:\\;\\<\\>\\=\\?\\@\\[\\]\\{\\}\\\\\\\\\\^\\_\\`\\~]";
    public static final String NUMBER_REGEX = "[\\d]*(\\d+)";
    private static final List<Symbol> SYMBOLS = new ArrayList<>();
    private static final Map<Integer, List<Integer>> SYMBOL_MAP = new HashMap<>();
    private static final List<PartNumber> PART_NUMBERS = new ArrayList<>();

    public static void main(String[] args) {
        final Integer answer1 = part1("day03.txt");
        log.info("The sum of all of the part numbers in the engine schematic is {}", answer1);
    }

    public static Integer part1(String fileName) {
        List<String> lines = loadInput(fileName);
        findSymbols(lines);
        findParts(lines);

        log.info("part numbers: {}", PART_NUMBERS);
        return PART_NUMBERS.stream()
                .map(PartNumber::value)
                .mapToInt(Integer::intValue)
                .sum();
    }

    private static void findSymbols(final List<String> lines) {
        Pattern isSymbol = Pattern.compile(SYMBOL_REGEX);
        for (int y = 0; y < lines.size(); y++) {
            final String line = lines.get(y);
            Matcher matcher = isSymbol.matcher(line);
            List<Integer> symbolsOnRow = new ArrayList<>();
            int currentX = 0;
            while (matcher.find()) {
                int x = line.indexOf(matcher.group(), currentX);
                log.info("found {} at {}.{}", matcher.group(), x, y);
                SYMBOLS.add(new Symbol(x, y));
                symbolsOnRow.add(x);
                currentX = matcher.end();
            }
            if (!symbolsOnRow.isEmpty()) {
                SYMBOL_MAP.put(y, symbolsOnRow);
            }
        }
        log.info("symbol coordinates: {}", SYMBOLS);
        log.info("symbol map: {}", SYMBOL_MAP);
    }

    private static void findParts(final List<String> lines) {
        Pattern isNumber = Pattern.compile(NUMBER_REGEX);
        for (int y = 0; y < lines.size(); y++) {
            final String line = lines.get(y);
            Matcher matcher = isNumber.matcher(line);
            int currentX = 0;
            while (matcher.find()) {
                int x = line.indexOf(matcher.group(), currentX);
                int xMax = x + matcher.group().length();
                log.info("found {} at {}.{}", matcher.group(), y, x);
                final PartNumber possiblePart = new PartNumber(Integer.parseInt(matcher.group()), x - 1, xMax, y);
                if (inYRange(possiblePart.y)) {
                    checkCoords(possiblePart);
                }
                currentX = matcher.end();
            }
        }
    }

    private static boolean inYRange(final int y) {
        return SYMBOL_MAP.containsKey(y) || SYMBOL_MAP.containsKey(y - 1) || SYMBOL_MAP.containsKey(y + 1);
    }

    private static void checkCoords(PartNumber possiblePart) {
        List<Integer> xCoords = new ArrayList<>();
        xCoords.addAll(getXCoordIfNotNull(possiblePart.y - 1));
        xCoords.addAll(getXCoordIfNotNull(possiblePart.y));
        xCoords.addAll(getXCoordIfNotNull(possiblePart.y + 1));
        for (Integer symbolX : xCoords) {
            if (symbolX >= possiblePart.xMin && symbolX <= possiblePart.xMax) {
                log.info("VALID PART {}", possiblePart.value);
                PART_NUMBERS.add(possiblePart);
                break;
            }
        }
    }

    private static List<Integer> getXCoordIfNotNull(final Integer y) {
        return SYMBOL_MAP.get(y) == null ? Collections.emptyList() : SYMBOL_MAP.get(y);
    }


    public record PartNumber(Integer value, int xMin, int xMax, int y) {

    }

    public record Symbol(int x, int y) {
    }
}


