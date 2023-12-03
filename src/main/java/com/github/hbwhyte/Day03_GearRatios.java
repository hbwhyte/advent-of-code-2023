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

    public static final String ALL_SYMBOL_REGEX = "[\\!\\\"\\#\\$\\%\\&\\'\\(\\)\\*\\+\\,\\-\\/\\:\\;\\<\\>\\=\\?\\@\\[\\]\\{\\}\\\\\\\\\\^\\_\\`\\~]";
    public static final String GEAR_REGEX = "[\\*]";
    public static final String NUMBER_REGEX = "[\\d]*(\\d+)";
    private static final Map<Integer, List<Integer>> SYMBOL_MAP = new HashMap<>();
    private static final List<Gear> GEARS = new ArrayList<>();
    private static final List<PartNumber> PART_NUMBERS = new ArrayList<>();
    private static final Map<Integer, List<PartNumber>> PART_NUMBERS_MAP = new HashMap<>();

    public static void main(String[] args) {
        final Integer answer1 = part2("day03.txt");
        log.info("The sum of all of the part numbers in the engine schematic is {}", answer1);
    }

    public static Integer part1(String fileName) {
        List<String> lines = loadInput(fileName);
        findSymbols(lines, ALL_SYMBOL_REGEX);
        findParts(lines);

        log.info("part numbers: {}", PART_NUMBERS);
        return PART_NUMBERS.stream()
                .map(PartNumber::value)
                .mapToInt(Integer::intValue)
                .sum();
    }

    public static Integer part2(String fileName) {
        PART_NUMBERS.clear();
        List<String> lines = loadInput(fileName);
        findSymbols(lines, GEAR_REGEX);
        findParts(lines);

        log.info("part numbers: {}", PART_NUMBERS);
        log.info("part numbers map: {}", PART_NUMBERS_MAP);
        final List<Integer> gearRatios = findGears();
        log.info("gear ratios: {}", gearRatios);
        return gearRatios.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    private static List<Integer> findGears() {
        final List<Integer> gearRatios = new ArrayList<>();
        for(Gear gear : GEARS) {
            List<PartNumber> nearbyParts = new ArrayList<>();
            List<PartNumber> validParts = new ArrayList<>();
            nearbyParts.addAll(getPartIfNotNull(gear.y - 1));
            nearbyParts.addAll(getPartIfNotNull(gear.y));
            nearbyParts.addAll(getPartIfNotNull(gear.y + 1));
            for (PartNumber part : nearbyParts) {
                if (part.xMin <= gear.x && part.xMax >= gear.x) {
                    log.info("NUMBER NEAR GEAR {}", part.value);
                    validParts.add(part);
                }
            }
            if (validParts.size() == 2) {
                log.info("Found valid gear, multiplying {} * {}", validParts.get(0).value, validParts.get(1).value);
                final Integer ratio = validParts.get(0).value * validParts.get(1).value;
                gearRatios.add(ratio);
            } else {
                log.warn("Invalid number of gears {}", validParts);
            }
        }
        return gearRatios;
    }



    private static void findSymbols(final List<String> lines, final String regex) {
        SYMBOL_MAP.clear();
        Pattern isSymbol = Pattern.compile(regex);
        for (int y = 0; y < lines.size(); y++) {
            final String line = lines.get(y);
            Matcher matcher = isSymbol.matcher(line);
            List<Integer> symbolsOnRow = new ArrayList<>();
            int currentX = 0;
            while (matcher.find()) {
                int x = line.indexOf(matcher.group(), currentX);
                log.info("found {} at {}.{}", matcher.group(), x, y);
                symbolsOnRow.add(x);
                currentX = matcher.end();
                GEARS.add(Gear.createGear(x, y));
            }
            if (!symbolsOnRow.isEmpty()) {
                SYMBOL_MAP.put(y, symbolsOnRow);
            }
        }
        log.info("symbol map: {}", SYMBOL_MAP);
        log.info("Gears: {}", GEARS);
    }

    private static void findParts(final List<String> lines) {
        Pattern isNumber = Pattern.compile(NUMBER_REGEX);
        for (int y = 0; y < lines.size(); y++) {
            final String line = lines.get(y);
            Matcher matcher = isNumber.matcher(line);
            int currentX = 0;
            List<PartNumber> numbersOnRow = new ArrayList<>();
            while (matcher.find()) {
                int x = line.indexOf(matcher.group(), currentX);
                int xMax = x + matcher.group().length();
                log.info("found {} at {}.{}", matcher.group(), y, x);
                final PartNumber possiblePart = new PartNumber(Integer.parseInt(matcher.group()), x - 1, xMax, y);
                if (inYRange(possiblePart.y) && checkCoords(possiblePart)) {
                    PART_NUMBERS.add(possiblePart);
                    numbersOnRow.add(possiblePart);
                }
                currentX = matcher.end();
                PART_NUMBERS_MAP.put(y, numbersOnRow);
            }
        }
    }

    private static boolean inYRange(final int y) {
        return SYMBOL_MAP.containsKey(y) || SYMBOL_MAP.containsKey(y - 1) || SYMBOL_MAP.containsKey(y + 1);
    }

    private static boolean checkCoords(PartNumber possiblePart) {
        List<Integer> xCoords = new ArrayList<>();
        xCoords.addAll(getXCoordIfNotNull(possiblePart.y - 1));
        xCoords.addAll(getXCoordIfNotNull(possiblePart.y));
        xCoords.addAll(getXCoordIfNotNull(possiblePart.y + 1));
        for (Integer symbolX : xCoords) {
            if (symbolX >= possiblePart.xMin && symbolX <= possiblePart.xMax) {
                log.info("VALID PART {}", possiblePart.value);
                return true;
            }
        }
        return false;
    }

    private static List<Integer> getXCoordIfNotNull(final Integer y) {
        return SYMBOL_MAP.get(y) == null ? Collections.emptyList() : SYMBOL_MAP.get(y);
    }

    private static List<PartNumber> getPartIfNotNull(final Integer y) {
        return PART_NUMBERS_MAP.get(y) == null ? Collections.emptyList() : PART_NUMBERS_MAP.get(y);
    }


    public record PartNumber(Integer value, int xMin, int xMax, int y) {
    }
    public record Gear(int x, int y, List<Integer> nearbyNumbers) {
        public static Gear createGear(int x, int y) {
            return new Gear(x, y, new ArrayList<>());
        }
    }
}


