package code.day2;

import com.google.common.collect.Multiset;

final class Draws {

  private Draws() {}

  public static DrawBuilder newBuilder() {
    return new DrawBuilder();
  }

  public static long power(Multiset<Color> draw) {
    return draw.entrySet().stream()
        .mapToLong(Multiset.Entry::getCount)
        .reduce(1, Math::multiplyExact);
  }

}
