package code.day1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import org.junit.jupiter.api.Test;

public class Day1Tests {

  private static final Path INPUT = Path.of("src/test/resources/day-1.txt");

  @Test
  public void digits() {
    assertEquals(53194, Day1.digits().compute(INPUT));
  }

  @Test
  public void digitsAndWords() {
    assertEquals(54249, Day1.digitsAndWords().compute(INPUT));
  }
}
