package code.day3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import org.junit.jupiter.api.Test;

public class Day3Tests {

  private static final Path INPUT = Path.of("src/test/resources/day-3.txt");

  @Test
  public void sumPartNumbers() {
    assertEquals(556057, new Day3().sumPartNumbers(INPUT));
  }

  @Test
  public void sumGearRatios() {
    assertEquals(82824352, new Day3().sumGearRatios(INPUT));
  }
}
