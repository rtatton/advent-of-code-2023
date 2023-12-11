package code.day2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import org.junit.jupiter.api.Test;

public class Day2Tests {

  private static final Path INPUT = Path.of("src/test/resources/day-2.txt");

  @Test
  public void sumOfGameIdsPermitting() {
    var draw =
        Draws.newBuilder().add(Color.RED, 12).add(Color.GREEN, 13).add(Color.BLUE, 14).build();
    assertEquals(2617, Day2.sumOfGameIdsPermitting(draw).compute(INPUT));
  }

  @Test
  public void powerSumOfMaxPossibleDraws() {
    assertEquals(59795, Day2.powerSumOfMaxPossibleDraws().compute(INPUT));
  }
}
