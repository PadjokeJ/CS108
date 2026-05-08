package dev.padjokej.week10;

import java.util.*;

public class LZWConcreteDecoder implements LZWDecoder {
    private final SortedSet<Character> alphabet;
    private final int maxSize;

    public LZWConcreteDecoder(SortedSet<Character> alphabet, int maxSize) {
        this.alphabet = alphabet;
        this.maxSize = maxSize;
    }

    @Override
    public String decode(List<Integer> l) {
        StringBuilder sb = new StringBuilder();

        List<String> prefixes = new ArrayList<>();
        alphabet.forEach(c ->
                prefixes.add(c.toString()));

        String prev = "";
        for (int v : l) {
            String s = (v < prefixes.size()) ? prefixes.get(v) : prev + prev.charAt(0);

            if (prefixes.size() < maxSize && !prev.isEmpty()) {
                prefixes.add(prev + s.charAt(0));
            }

            sb.append(s);
            prev = s;
        }

        return sb.toString();
    }
}
