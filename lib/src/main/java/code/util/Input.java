package code.util;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Function;
import java.util.stream.Stream;

public final class Input {

  private Input() {}

  public static <T> T compute(Path input, Function<Stream<String>, T> computer) {
    try (var reader = Files.newBufferedReader(input)) {
      return computer.apply(reader.lines());
    } catch (IOException exception) {
      throw new UncheckedIOException(exception);
    }
  }
}
