package code.day3;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import javax.annotation.Nullable;

public record Day3() {

  private static final Pattern NUMBERS = Pattern.compile("\\d+");
  private static final Pattern SYMBOLS = Pattern.compile("[^.\\d]");

  public static void main(String[] args) {
    var input = Path.of("lib/src/main/resources/day-3.txt");
    part1(input);
  }

  private static void part1(Path input) {
    System.out.println("Sum of part numbers: " + new Day3().sumPartNumbers(input));
  }

  public long sumPartNumbers(Path input) {
    try (var reader = Files.newBufferedReader(input)) {
      var previous = (String) null;
      var current = reader.readLine();
      var next = reader.readLine();
      var sum = 0L;
      while (current != null) {
        // This may look like double counting, but it's not.
        // We need to compare the current line against all three to
        // ensure we don't miss a part number.
        sum += sumPartNumbers(current, previous);
        sum += sumPartNumbers(current, current);
        sum += sumPartNumbers(current, next);
        previous = current;
        current = next;
        next = reader.readLine();
      }
      return sum;
    } catch (IOException exception) {
      throw new UncheckedIOException(exception);
    }
  }

  private long sumPartNumbers(String numbers, @Nullable String symbols) {
    // Check for null here to keep the loop above easier to read.
    if (symbols == null) {
      return 0;
    }
    var numberMatches = findMatches(numbers, NUMBERS, 0, 0);
    // Expand match indices in either direction to find adjacent part numbers.
    var symbolMatches = findMatches(symbols, SYMBOLS, 1, 1);
    retainKeys(numberMatches, symbolMatches);
    return sumValues(numberMatches);
  }

  private RangeMap<Integer, String> findMatches(
      String line, Pattern pattern, int before, int after) {
    return pattern.matcher(line).results().collect(toMatchMap(before, after));
  }

  private Collector<MatchResult, ?, RangeMap<Integer, String>> toMatchMap(int before, int after) {
    return Collector.of(TreeRangeMap::create, accumulate(before, after), combine());
  }

  private BiConsumer<RangeMap<Integer, String>, MatchResult> accumulate(int before, int after) {
    return (matches, result) -> matches.put(indices(result, before, after), result.group());
  }

  private Range<Integer> indices(MatchResult result, int before, int after) {
    // MatchResult.end() is the first index AFTER the match has ended...not sure why.
    return Range.closed(result.start() - before, result.end() - 1 + after);
  }

  private BinaryOperator<RangeMap<Integer, String>> combine() {
    return (left, right) -> {
      left.putAll(right);
      return left;
    };
  }

  @SuppressWarnings("rawtypes")
  private <K extends Comparable> void retainKeys(RangeMap<K, ?> map, RangeMap<K, ?> filter) {
    map.asMapOfRanges().keySet().removeIf(range -> !contains(filter, range));
  }

  @SuppressWarnings("rawtypes")
  private <K extends Comparable> boolean contains(RangeMap<K, ?> map, Range<K> range) {
    return !map.subRangeMap(range).asMapOfRanges().isEmpty();
  }

  private long sumValues(RangeMap<?, String> map) {
    return map.asMapOfRanges().values().stream().mapToLong(Long::parseLong).sum();
  }
}
