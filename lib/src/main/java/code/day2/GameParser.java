package code.day2;

import com.google.common.collect.Multiset;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

record GameParser() {

  private static final Pattern GAME_ID_PATTERN = gameIdPattern();
  private static final Pattern COLOR_COUNT_PATTERN = colorCountPattern();

  public Game parse(String game) {
    var gameId = parseGameId(game);
    var draws = parseDraws(game);
    return new Game(gameId, draws);
  }

  @SuppressWarnings("ResultOfMethodCallIgnored")
  private long parseGameId(String game) {
    var matcher = GAME_ID_PATTERN.matcher(game);
    matcher.find();
    return Long.parseLong(matcher.group(1));
  }

  private List<Multiset<Color>> parseDraws(String game) {
    return Stream.of(game.split(";")).map(this::parseDraw).toList();
  }

  private Multiset<Color> parseDraw(String draw) {
    var builder = Draws.newBuilder();
    var matcher = COLOR_COUNT_PATTERN.matcher(draw);
    while (matcher.find()) {
      var count = Integer.parseInt(matcher.group(1));
      var color = Color.from(matcher.group(2));
      builder.add(color, count);
    }
    return builder.build();
  }

  private static Pattern gameIdPattern() {
    return Pattern.compile("game\\s*(\\d+)", Pattern.CASE_INSENSITIVE);
  }

  private static Pattern colorCountPattern() {
    var colors = Color.stream().map(Color::toString).collect(Collectors.joining("|"));
    return Pattern.compile("(\\d+)\\s*(" + colors + ")", Pattern.CASE_INSENSITIVE);
  }
}
