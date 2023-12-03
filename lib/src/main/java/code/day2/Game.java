package code.day2;

import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

record Game(long id, List<Multiset<Color>> draws) {

  private static final GameParser PARSER = new GameParser();

  public static Game parse(String game) {
    return PARSER.parse(game);
  }

  public boolean permits(Multiset<Color> draw) {
    return draws.stream().allMatch(d -> Multisets.containsOccurrences(draw, d));
  }

  public Multiset<Color> maxPossibleDraw() {
    var max = draws.stream().map(Multiset::entrySet).flatMap(Set::stream).collect(toMaxCounts());
    return Draws.newBuilder().addAll(max).build();
  }

  @Override
  public int hashCode() {
    return Long.hashCode(id);
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof Game game && id == game.id;
  }

  private Collector<Multiset.Entry<Color>, ?, Map<Color, Integer>> toMaxCounts() {
    return Collectors.toMap(
        Multiset.Entry::getElement,
        Multiset.Entry::getCount,
        Math::max,
        () -> new EnumMap<>(Color.class));
  }
}
