package dev.padjokej.midterm.year2026;

public class Concat<E> extends AbstractImmuList<E> {
    AbstractImmuList<E> l1, l2;

    public Concat(AbstractImmuList<E> o1, AbstractImmuList<E> o2) {
        l1 = o1;
        l2 = o2;
    }

    @Override
    public int size() {
        return l1.size() + l2.size();
    }

    @Override
    public E get(int i) {
        return (i < l1.size()) ? l1.get(i) : l2.get(i - l1.size());
    }
}
