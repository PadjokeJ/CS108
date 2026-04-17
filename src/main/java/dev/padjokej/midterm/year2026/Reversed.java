package dev.padjokej.midterm.year2026;

public class Reversed<E> extends AbstractImmuList<E> {
    private final AbstractImmuList<E> list;
    public Reversed(AbstractImmuList<E> list) {
        this.list = list;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public E get(int i) {
        return list.get(size() - 1 - i);
    }
}
