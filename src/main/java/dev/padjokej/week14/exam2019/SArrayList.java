package dev.padjokej.week14.exam2019;

import java.util.NoSuchElementException;

public class SArrayList<E> implements SList<E> {
    private int size = 0;
    private E[] arr = (E[]) new Object[10];

    @Override
    public SListIterator<E> listerator() {
        return new SListIterator<E>() {
            int i = -1;

            @Override
            public boolean hasNext() {
                return i < size;
            }

            @Override
            public E next() {
                if (!hasNext())
                    throw new NoSuchElementException();

                return arr[++i];
            }

            @Override
            public int nextIndex() {
                if (!hasNext()) return size;
                return i + 1;
            }

            @Override
            public boolean hasPrevious() {
                return i > 0;
            }

            @Override
            public E previous() {
                if (!hasPrevious())
                    throw new NoSuchElementException();

                return arr[--i];
            }

            @Override
            public int previousIndex() {
                if (!hasPrevious()) return -1;
                return i - 1;
            }

            @Override
            public void set(E newVal) {
                if (i == -1)
                    throw new IllegalStateException();

                arr[i] = newVal;
            }
        };
    }
}
