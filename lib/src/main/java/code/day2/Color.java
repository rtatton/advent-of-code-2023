package code.day2;

import java.util.stream.Stream;

enum Color {
  BLUE,
  RED,
  GREEN;

  public static Stream<Color> stream() {
    return Stream.of(values());
  }

  public static Color from(String value) {
    return valueOf(value.toUpperCase());
  }
}
