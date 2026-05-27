package dev.padjokej.week14.exam2019;

import java.util.Set;

public final class CheckingSerde<T> implements AsciiSerde<T> {
    private final AsciiSerde<T> as;

    public CheckingSerde (AsciiSerde<T> underlyingSerde){
        as = underlyingSerde;

        for (char c : as.alphabet()) {
            if (!AsciiSerde.isAscii(c))
                throw new IllegalSerdeException("");
        }
    }

    @Override
    public Set<Character> alphabet() {
        return as.alphabet();
    }

    @Override
    public String serialize(T value) {
        String s = as.serialize(value);
        Set<Character> al = alphabet();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (!al.contains(c))
                throw new IllegalSerdeException("");
        }

        return s;
    }

    @Override
    public T deserialize(String s) {
        return as.deserialize(s);
    }
}
