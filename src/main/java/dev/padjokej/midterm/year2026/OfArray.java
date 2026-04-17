package dev.padjokej.midterm.year2026;

public final class OfArray<E> extends AbstractImmuList<E> {
    private final E[] array;

    public OfArray (E[] arr) {
        array = arr.clone();
    }

    @Override
    public int size() {
        return array.length;
    }

    @Override
    public E get(int i) {
        return array[i];
    }
}
