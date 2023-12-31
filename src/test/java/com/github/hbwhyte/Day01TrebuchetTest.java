package com.github.hbwhyte;

import com.github.hbwhyte.Day01_Trebuchet;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day01TrebuchetTest {

    @Test
    void trebuchet_part1() {
        assertThat(Day01_Trebuchet.calibrateTrebuchet("day01_part1.txt")).isEqualTo(142);
    }

    @Test
    void trebuchet_part2() {
        assertThat(Day01_Trebuchet.calibrateTrebuchet("day01_part2.txt")).isEqualTo(281);
    }
}
