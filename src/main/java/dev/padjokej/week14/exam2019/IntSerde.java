package dev.padjokej.week14.exam2019;

import java.util.Set;
import java.util.TreeSet;

public class IntSerde implements AsciiSerde<Integer> {
    @Override
    public Set<Character> alphabet() {
        Set<Character> alp = new TreeSet<>();
        alp.add('-');

        for (int i = 0; i < 10; i++) {
            alp.add(Integer.toString(i).charAt(0));
        }

        return alp;
    }

    @Override
    public String serialize(Integer value) {
        return Integer.toString(value);
    }

    @Override
    public Integer deserialize(String str) {
        int v = Integer.parseInt(str);

        if (!serialize(v).equals(str))
            throw new IllegalArgumentException();

        return v;
    }
}
