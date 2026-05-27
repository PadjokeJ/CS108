package dev.padjokej.week14.exam2019;

import java.util.Set;

public interface AsciiSerde<T> {
    public Set<Character> alphabet();
    public String serialize(T value);
    public T deserialize(String str);

    public static boolean isAscii(char c) {
        return c < 128;
    }
}
