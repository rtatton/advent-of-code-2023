package code.day4;

import com.google.common.collect.Sets;

import java.util.Set;

record Card(long id, Set<Long> winning, Set<Long> hand) {

    public static Card parse(String string) {
        return new CardParser().parse(string);
    }

    public long value() {
        return (long) Math.pow(2, nMatches() - 1);
    }

    public int nMatches() {
        return Sets.intersection(winning, hand).size();
    }
}
