package code.day4;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

record CardParser() {

    public Card parse(String string) {
        var parts = splitOnWhitespace(string, ":");
        var id = Long.parseLong(splitOnWhitespace(parts[0], "Card")[1]);
        var numbers = splitOnWhitespace(parts[1], "\\|");
        var winning = parseNumbers(numbers[0]);
        var hand = parseNumbers(numbers[1]);
        return new Card(id, winning, hand);
    }

    private Set<Long> parseNumbers(String numbers) {
        return Stream.of(splitOnWhitespace(numbers)).map(Long::parseLong).collect(Collectors.toUnmodifiableSet());
    }

    private String[] splitOnWhitespace(String string) {
        return splitOnWhitespace(string, "");
    }

    private String[] splitOnWhitespace(String string, String prefix) {
        return string.split(prefix + "\\s+");
    }
}
