package dev.padjokej.week14.exam2025;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;

public final class ByteIntTrie {
    private final Node root = new Node();
    private int size = 0;

    public int size() { return size; }

    public int get(byte[] key) {
        Node curr = root;

        for (byte k : key) {
            curr = curr.children[Byte.toUnsignedInt(k)];
            if (curr == null) return Node.NO_VALUE;
        }
        return curr.value;
    }

    public void put(byte[] key, int value) {
        if (value == Node.NO_VALUE)
            throw new IllegalArgumentException();

        Node curr = root;

        for (byte k : key) {
            int i = Byte.toUnsignedInt(k);
            if (curr.children[i] == null)
                curr.children[i] = new Node();
            curr = curr.children[i];
        }

        if (curr.value == Node.NO_VALUE) size++;

        curr.value = value;
    }

    public void forEach(BiConsumer<byte[], Integer> consumer) {
        List<KeyValue> visit = new ArrayList<>();
        visit.add(new KeyValue(new byte[0], root.value));

        while (!visit.isEmpty()) {
            KeyValue kv = visit.removeLast();
            consumer.accept(kv.key, kv.value);

            byte[] newKey = Arrays.copyOf(kv.key, kv.key.length + 1);
            for (int i = 0; i < Byte.toUnsignedInt(Byte.MAX_VALUE); i++) {
                newKey[newKey.length - 1] = (byte) i;

                int v = get(newKey);
                if (v != Node.NO_VALUE)
                    visit.add(new KeyValue(newKey.clone(), v));
            }
        }
    }

    public Iterator<KeyValue> iterator() {
        return new Iterator<>() {
            private List<KeyValue> toVisit = new ArrayList<>(List.of(new KeyValue(new byte[0], root.value)));

            @Override
            public boolean hasNext() {
                return !toVisit.isEmpty();
            }

            @Override
            public KeyValue next() {
                KeyValue kv = toVisit.removeLast();

                byte[] newKey = Arrays.copyOf(kv.key, kv.key.length + 1);
                for (int i = 0; i < 256; i++) {
                    newKey[newKey.length - 1] = (byte) i;

                    int v = get(newKey);
                    if (v != Node.NO_VALUE)
                        toVisit.add(new KeyValue(newKey.clone(), v));
                }

                return kv;
            }
        };
    }

    public record KeyValue(byte[] key, int value) { }

    private static final class Node {
        static final int NO_VALUE = Integer.MIN_VALUE;

        final Node[] children = new Node[256];

        int value = NO_VALUE;
    }
}
