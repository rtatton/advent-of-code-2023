package code.day3;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

record Day3() {

  private static final Pattern NUMBERS = Pattern.compile("\\d+");
  private static final Pattern SYMBOLS = Pattern.compile("[^.\\d]");

  public static void main(String[] args) {
    var input = Path.of("lib/src/main/resources/day-3.txt");
    part1(input);
    part2(input);
  }

  private static void part1(Path input) {
    System.out.println("Sum of part numbers: " + new Day3().sumPartNumbers(input));
  }

  private static void part2(Path input) {
    System.out.println("Sum of gear ratios: " + new Day3().sumGearRatios(input));
  }

  public long sumGearRatios(Path input) {
    try (var reader = Files.newBufferedReader(input)) {
      var previous = (String) null;
      var current = reader.readLine();
      var next = reader.readLine();
      var sum = 0L;
      while (current != null) {
        var symbols =
            merge(
                findPartNumberSymbols(current, previous),
                findPartNumberSymbols(current, current),
                findPartNumberSymbols(current, next));
        sum += sumGearRatios(symbols);
        previous = current;
        current = next;
        next = reader.readLine();
      }
      return sum;
    } catch (IOException exception) {
      throw new UncheckedIOException(exception);
    }
  }

  public long sumPartNumbers(Path input) {
    try (var reader = Files.newBufferedReader(input)) {
      var previous = (String) null;
      var current = reader.readLine();
      var next = reader.readLine();
      var sum = 0L;
      while (current != null) {
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

  private long sumPartNumbers(String current, @Nullable String other) {
    return findPartNumberSymbols(current, other).asMapOfRanges().values().stream()
        .map(Symbol::partNumbers)
        .flatMap(Collection::stream)
        .mapToLong(n -> n)
        .sum();
  }

  private RangeMap<Integer, Symbol> findPartNumberSymbols(String current, @Nullable String other) {
    // Check for null here to keep the loop above easier to read.
    if (other == null) {
      return TreeRangeMap.create();
    }
    // Expand match indices in either direction to find adjacent part numbers.
    var symbolMatches = findMatches(current, SYMBOLS, 1, 1);
    var numberMatches = findMatches(other, NUMBERS, 0, 0);
    return findPartNumberSymbols(symbolMatches, numberMatches);
  }

  private RangeMap<Integer, String> findMatches(
      String line, Pattern pattern, int before, int after) {
    var matches = TreeRangeMap.<Integer, String>create();
    var matcher = pattern.matcher(line);
    while (matcher.find()) {
      var result = matcher.toMatchResult();
      // MatchResult.end() is the first index AFTER the match has ended...not sure why.
      var indices = Range.closed(result.start() - before, result.end() - 1 + after);
      matches.put(indices, result.group());
    }
    return matches;
  }

  private RangeMap<Integer, Symbol> findPartNumberSymbols(
      RangeMap<Integer, String> symbols, RangeMap<Integer, String> numbers) {
    var result = TreeRangeMap.<Integer, Symbol>create();
    for (var entry : symbols.asMapOfRanges().entrySet()) {
      var symbolIndices = entry.getKey();
      var partNumberStrings = numbers.subRangeMap(symbolIndices).asMapOfRanges();
      if (!partNumberStrings.isEmpty()) {
        var symbolValue = entry.getValue();
        var partNumbers = partNumberStrings.values().stream().map(Long::parseLong).toList();
        result.put(symbolIndices, new Symbol(symbolValue, partNumbers));
      }
    }
    return result;
  }

  @SafeVarargs
  private RangeMap<Integer, Symbol> merge(RangeMap<Integer, Symbol>... maps) {
    var merged = TreeRangeMap.<Integer, Symbol>create();
    for (var map : maps) {
      for (var entry : map.asMapOfRanges().entrySet()) {
        merged.merge(entry.getKey(), entry.getValue(), Symbol::merge);
      }
    }
    return merged;
  }

  private long sumGearRatios(RangeMap<Integer, Symbol> symbols) {
    return symbols.asMapOfRanges().values().stream()
        .filter(Symbol::isGear)
        .mapToLong(Symbol::gearRatio)
        .sum();
  }
}
