package code.day4;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day4Tests {

  private static final Path INPUT = Path.of("src/test/resources/day-4.txt");

  @Test
  public void sumCardValues() {
    assertEquals(21568, new Day4().sumCardValues(INPUT));
  }

  @Test
  public void totalScratchcards() {
    assertEquals(11827296, new Day4().totalScratchcards(INPUT));
  }
}
