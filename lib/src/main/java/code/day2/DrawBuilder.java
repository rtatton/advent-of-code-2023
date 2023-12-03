package code.day2;

import com.google.common.collect.EnumMultiset;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multiset;

import java.util.Map;

final class DrawBuilder {

    private final Multiset<Color> draw;

    public DrawBuilder() {
        this.draw = EnumMultiset.create(Color.class);
    }

    public DrawBuilder addAll(Map<Color, Integer> colors) {
        colors.forEach(this::add);
        return this;
    }

    public DrawBuilder add(Color color, int count) {
        draw.add(color, count);
        return this;
    }

    public Multiset<Color> build() {
        return ImmutableMultiset.copyOf(draw);
    }
}
