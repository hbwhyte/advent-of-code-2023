import com.github.hbwhyte.Day02_CubeConundrum;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day02_CubeConundrumTest {

    @Test
    void part01() {
        assertThat(Day02_CubeConundrum.calculate("day02_part1.txt")).isEqualTo(8);
    }
}
