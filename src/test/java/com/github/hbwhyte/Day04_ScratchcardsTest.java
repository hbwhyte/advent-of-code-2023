package com.github.hbwhyte;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class Day04_ScratchcardsTest {

    @Test
    void part1() {
        assertThat(Day04_Scratchcards.part1("day04.txt")).isEqualTo(13);
    }

    @Test
    void part2() {
        assertThat(Day04_Scratchcards.part2("day04.txt")).isEqualTo(30);
    }
}