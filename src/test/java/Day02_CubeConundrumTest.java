import com.github.hbwhyte.Day02_CubeConundrum;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day02_CubeConundrumTest {

    @Test
    void part01() {
        assertThat(Day02_CubeConundrum.part1("day02_part1.txt")).isEqualTo(8);
    }

    @Test
    void part02() {
        assertThat(Day02_CubeConundrum.part2("day02_part1.txt")).isEqualTo(2286);
    }
}
