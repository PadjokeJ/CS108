package dev.padjokej.week14.exam2019;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class BoolSerde implements AsciiSerde<Boolean> {
    public Set<Character> alphabet() {
        return new HashSet<>(Arrays.asList('T', 'F'));
    }
    public String serialize(Boolean value) {
        return value ? "T" : "F";
    }
    public Boolean deserialize(String str) {
        switch (str) {
            case "T": return true;
            case "F": return false;
            default: throw new IllegalArgumentException();
        }
    }
}
