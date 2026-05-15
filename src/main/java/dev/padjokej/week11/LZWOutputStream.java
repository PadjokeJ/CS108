package dev.padjokej.week11;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

public class LZWOutputStream extends OutputStream {
    private static final int MAX_SIZE = 1 << 12;
    private static final SortedSet<Integer> ALPHABET = generateByteAlphabet();

    private final Bits12OutputStream underlyingStream;
    private final Map<List<Integer>, Integer> prefixes;

    private final List<Integer> prefix;

    public LZWOutputStream(OutputStream writeStream) {
        underlyingStream = new Bits12OutputStream(writeStream);

        prefixes = new HashMap<>();
        ALPHABET.forEach(i ->
                prefixes.put(Collections.singletonList(i), i));

        prefix = new ArrayList<>();
    }

    @Override
    public void write(int i) throws IOException {
        int b = i & 0xFF;

        prefix.add(b);
        if (prefixes.containsKey(prefix))
            return;

        List<Integer> prev = prefix.subList(0, prefix.size() - 1);
        underlyingStream.writeU12(prefixes.get(prev));

        if (prefixes.size() < MAX_SIZE)
            prefixes.put(List.copyOf(prefix), prefixes.size());

        prev.clear();
    }

    @Override
    public void close() throws IOException {
        if (!prefix.isEmpty()) {
            underlyingStream.writeU12(prefixes.get(prefix));
        }
        underlyingStream.close();
    }

    public static SortedSet<Integer> generateByteAlphabet() {
        SortedSet<Integer> alph = new TreeSet<>();

        for (int i = 0; i < 256; i++) {
            alph.add(i);
        }

        return alph;
    }
}
