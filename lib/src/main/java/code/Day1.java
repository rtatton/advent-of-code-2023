package code;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public record Day1(Pattern pattern, int groupIndex) {

  private static final Map<String, String> WORD_TO_DIGIT = wordToDigit();

  public static Day1 digits() {
    return new Day1(Pattern.compile("\\d"), 0);
  }

  public static Day1 digitsAndWords() {
    var regex = "(?=(" + String.join("|", WORD_TO_DIGIT.keySet()) + "|\\d))";
    var pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    return new Day1(pattern, 1);
  }

  public static void main(String[] args) {
    var input = Path.of("lib/src/main/resources/day-1.txt");
    System.out.println("Digits: " + Day1.digits().compute(input));
    System.out.println("Digits and words: " + Day1.digitsAndWords().compute(input));
  }

  public long compute(Path input) {
    try (var reader = Files.newBufferedReader(input)) {
      return reader.lines().mapToLong(this::valueOf).sum();
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  private int valueOf(String line) {
    var digits = digits(line);
    return digits.isEmpty() ? 0 : digits.get(0) * 10 + digits.get(digits.size() - 1);
  }

  private List<Integer> digits(String line) {
    var matcher = pattern.matcher(line);
    var digits = new ArrayList<Integer>();
    while (matcher.find()) {
      var group = matcher.group(groupIndex);
      digits.add(toInt(group));
    }
    return digits;
  }

  private int toInt(String wordOrDigit) {
    return Integer.parseInt(WORD_TO_DIGIT.getOrDefault(wordOrDigit.toLowerCase(), wordOrDigit));
  }

  private static Map<String, String> wordToDigit() {
    return Map.ofEntries(
        Map.entry("zero", "0"),
        Map.entry("one", "1"),
        Map.entry("two", "2"),
        Map.entry("three", "3"),
        Map.entry("four", "4"),
        Map.entry("five", "5"),
        Map.entry("six", "6"),
        Map.entry("seven", "7"),
        Map.entry("eight", "8"),
        Map.entry("nine", "9"));
  }
}
