package dev.padjokej.midterm.year2026;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.BiConsumer;

public final class PkIntSet64 {
    public static final long EMPTY = 0L;

    public static long of(Iterable<Integer> elements) {
        Iterator<Integer> it = elements.iterator();
        long pkIntSet = EMPTY;
        while (it.hasNext()) {
            pkIntSet = add(pkIntSet, it.next());
        }
        return pkIntSet;
    }

    public static int size(long pkIntSet) {
        return Long.bitCount(pkIntSet);
    }

    public static long add(long pkIntSet, int i) {
        return pkIntSet | (1L << Objects.checkIndex(i, Long.SIZE));
    }

    public static void forEachIndexed(long pkIntSet, BiConsumer<Integer, Integer> consumer) {
        int index = 0;
        for (int i = 0; i < size(pkIntSet); i++) {
            if ((pkIntSet & (1L << i)) != EMPTY) consumer.accept(index++, i);
        }
    }

    public static int[] toArray(long pkIntSet) {
        int[] array = new int[size(pkIntSet)];
        forEachIndexed(pkIntSet, (i, e) -> array[i] = e);
        return array;
    }

}
