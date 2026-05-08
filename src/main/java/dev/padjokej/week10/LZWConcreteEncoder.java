package dev.padjokej.week10;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeMap;

public class LZWConcreteEncoder implements LZWEncoder {
    private final int maxSize;
    private final SortedSet<Character> alphabet;

    public LZWConcreteEncoder(SortedSet<Character> alphabet, int maxSize) {
        if (maxSize < alphabet.size())
            throw new IndexOutOfBoundsException("Max size must be big enough for the provided alphabet");
        this.maxSize = maxSize;
        this.alphabet = alphabet;
    }

    @Override
    public List<Integer> encode(String s) {
        List<Integer> l = new ArrayList<>();

        TreeMap<String, Integer> prefixes = new TreeMap<>();

        alphabet.forEach(c ->
                prefixes.put(Character.toString(c), prefixes.size()));

        int i = 0;
        while (i < s.length()) {
            int j = 1;
            String prefix = s.substring(i, i + j);
            while (i + j <= s.length()) {
                if (prefixes.containsKey(s.substring(i, i + j)))
                    prefix = s.substring(i, i + j);
                else
                    break;
                j++;
            }
            l.add(prefixes.get(prefix));
            if (prefixes.size() < maxSize) {
                int e = Math.min(i + j, s.length());
                prefixes.put(s.substring(i, e), prefixes.size());
            }

            i += prefix.length();
        }

        return l;
    }
}
