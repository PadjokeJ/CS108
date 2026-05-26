package dev.padjokej.week14.exam2019;

public interface SListIterator<E> {
    public boolean hasNext();
    public E next();
    public int nextIndex();
    public boolean hasPrevious();
    public E previous();
    public int previousIndex();
    public void set(E newValue);
}
