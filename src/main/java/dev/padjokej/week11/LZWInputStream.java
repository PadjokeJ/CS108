package dev.padjokej.week11;

import dev.padjokej.week12.BitsInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class LZWInputStream extends InputStream {
    private static final int MAX_SIZE = 1 << Short.SIZE;
    private static final SortedSet<Integer> ALPHABET = LZWOutputStream.generateByteAlphabet();
    private final BitsInputStream underlyingStream;

    private final List<List<Integer>> prefixes;
    private final Deque<Integer> contents;
    private List<Integer> prev;

    public LZWInputStream(InputStream inputStream) {
        underlyingStream = new BitsInputStream(inputStream);

        prefixes = new ArrayList<>(MAX_SIZE);
        ALPHABET.forEach(i -> prefixes.add(Collections.singletonList(i)));

        prev = new ArrayList<>();
        contents = new ArrayDeque<>();
    }

    @Override
    public int read() throws IOException {
        if (contents.isEmpty()) {
            boolean willAdd = prefixes.size() < MAX_SIZE && !prev.isEmpty();
            int v = underlyingStream.read(
                    LZWOutputStream.bitsToEncode(prefixes.size() + (willAdd ? 1 : 0)));

            // We have reached end of stream
            if (v == -1) return v;

            List<Integer> n = (v < prefixes.size()) ? prefixes.get(v)
                    : extendedPrefix(prev, prev.getFirst());

            if (willAdd) {
                prefixes.add(extendedPrefix(prev, n.getFirst()));
            }

            contents.addAll(n);
            prev = n;
        }

        return contents.removeFirst();
    }

    @Override
    public void close() throws IOException {
        underlyingStream.close();
    }

    private List<Integer> extendedPrefix(List<Integer> l1, int i) {
        List<Integer> n = new ArrayList<>(l1);
        n.add(i);

        return n;
    }
}
