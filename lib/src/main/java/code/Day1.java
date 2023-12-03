package code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day1 {

  public static void main(String[] args) {
    try (var reader = Files.newBufferedReader(Path.of("lib/src/main/resources/day-1.txt"))) {
      System.out.println(reader.lines().mapToLong(Day1::valueOf).sum());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static long valueOf(String line) {
    var digits = digits(line);
    if (digits.isEmpty()) {
      return 0;
    } else if (digits.size() == 1) {
      return Long.parseLong(digits.get(0) + digits.get(0));
    } else {
      return Long.parseLong(digits.get(0) + digits.get(digits.size() - 1));
    }
  }

  private static List<String> digits(String line) {
    var digits = new ArrayList<String>();
    for (char c : line.toCharArray()) {
      if (Character.isDigit(c)) {
        digits.add(String.valueOf(c));
      }
    }
    return digits;
  }
}
