package code.day1;

import code.util.Input;
import code.util.Patterns;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

// https://adventofcode.com/2023/day/1
record Day1(Pattern pattern, int groupIndex) {

  private static final Map<String, String> WORD_TO_DIGIT = wordToDigit();

  public static Day1 digits() {
    return new Day1(Patterns.caseInsensitive("\\d"), 0);
  }

  public static Day1 digitsAndWords() {
    var regex = "(?=(" + String.join("|", WORD_TO_DIGIT.keySet()) + "|\\d))";
    return new Day1(Patterns.caseInsensitive(regex), 1);
  }

  public static void main(String[] args) {
    var input = Path.of("lib/src/main/resources/day-1.txt");
    part1(input);
    part2(input);
  }

  private static void part1(Path input) {
    System.out.println("Digits: " + Day1.digits().compute(input));
  }

  private static void part2(Path input) {
    System.out.println("Digits and words: " + Day1.digitsAndWords().compute(input));
  }

  public long compute(Path input) {
    return Input.compute(input, i -> i.mapToLong(this::valueOf).sum());
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