package code.util;

import java.util.regex.Pattern;

public final class Patterns {

  private Patterns() {}

  public static Pattern caseInsensitive(String regex) {
    return Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
  }
}
