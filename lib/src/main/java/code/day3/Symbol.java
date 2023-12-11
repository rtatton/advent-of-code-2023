package code.day3;

import java.util.List;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public record Symbol(String value, List<Long> partNumbers) {

  public long gearRatio() {
    return partNumbers.isEmpty() ? 0 : partNumbers.stream().reduce(1L, Math::multiplyExact);
  }

  public boolean isGear() {
    return value.equals("*") && partNumbers.size() == 2;
  }

  public Symbol merge(@Nullable Symbol symbol) {
    if (symbol == null) {
      return this;
    }
    var merged = Stream.concat(partNumbers.stream(), symbol.partNumbers.stream()).toList();
    return new Symbol(value, merged);
  }
}
