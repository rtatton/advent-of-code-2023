package code.day4;

import code.util.Input;
import com.google.common.collect.TreeMultiset;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

// https://adventofcode.com/2023/day/4
record Day4() {

    public static void main(String[] args) {
        var input = Path.of("lib/src/main/resources/day-4.txt");
        part1(input);
        part2(input);
    }

    private static void part1(Path input) {
        System.out.println("Sum of card values: " + new Day4().sumCardValues(input));
    }

    private static void part2(Path input) {
        System.out.println("Number of scratchcards: " + new Day4().totalScratchcards(input));
    }

    public long sumCardValues(Path input) {
        return Input.compute(input, i -> i.map(Card::parse).mapToLong(Card::value).sum());
    }

    public long totalScratchcards(Path input) {
        try(var reader = Files.newBufferedReader(input)) {
            var total = 0L;
            var copies = TreeMultiset.<Long>create();
            var line = reader.readLine();
            while (line != null) {
                var card = Card.parse(line);
                var id = card.id();
                var nMatches = card.nMatches();
                var nCopies = copies.setCount(id, 0) + 1;
                for (long i = 1; i <= nMatches; i++) {
                    copies.add(id + i, nCopies);
                }
                total += nCopies;
                line = reader.readLine();
            }
            return total;
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }
}
