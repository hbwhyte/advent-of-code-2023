package com.github.hbwhyte;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day03_GearRatiosTest {

    @Test
    void part1() {
        assertThat(Day03_GearRatios.part1("day03.txt")).isEqualTo(4361);
    }

    @Test
    void part2() {
        assertThat(Day03_GearRatios.part1("day03.txt")).isEqualTo(467835);
    }
}
//560712 is too high
