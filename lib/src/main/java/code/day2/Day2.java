package code.day2;

import code.util.Input;
import com.google.common.collect.Multiset;
import java.nio.file.Path;
import java.util.function.Function;
import java.util.stream.Stream;

// https://adventofcode.com/2023/day/2
record Day2(Function<Stream<String>, Long> computer) {

  public static Day2 sumOfGameIdsPermitting(Multiset<Color> draw) {
    return new Day2(i -> i.map(Game::parse).filter(g -> g.permits(draw)).mapToLong(Game::id).sum());
  }

  public static Day2 powerSumOfMaxPossibleDraws() {
    return new Day2(
        i -> i.map(Game::parse).map(Game::maxPossibleDraw).mapToLong(Draws::power).sum());
  }

  public static void main(String[] args) {
    var input = Path.of("lib/src/main/resources/day-2.txt");
    part1(input);
    part2(input);
  }

  private static void part1(Path input) {
    var draw = part1Draw();
    var result = Day2.sumOfGameIdsPermitting(draw).compute(input);
    System.out.println("Sum of game IDs: " + result);
  }

  private static Multiset<Color> part1Draw() {
    return Draws.newBuilder().add(Color.RED, 12).add(Color.GREEN, 13).add(Color.BLUE, 14).build();
  }

  private static void part2(Path input) {
    var result = Day2.powerSumOfMaxPossibleDraws().compute(input);
    System.out.println("Power sum of max possible draws: " + result);
  }

  public long compute(Path input) {
    return Input.compute(input, computer);
  }
}
