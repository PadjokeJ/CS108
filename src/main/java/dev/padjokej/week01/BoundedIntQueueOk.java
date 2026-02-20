package dev.padjokej.week01;

/// Bounded Int Queue
///
/// @author Jonatan Pfister
public class BoundedIntQueueOk implements BoundedIntQueue {
    int maxCapacity;

    int[] queue;

    int size;
    int lastPointerIndex = 0;
    int headPointerIndex = 0;

    public BoundedIntQueueOk(int capacity) {
        if (capacity < 0)
            throw new IllegalArgumentException("Capacity cannot be negative");

        maxCapacity = capacity;
        queue = new int[capacity];
    }

    /**
     * Retourne la capacité de la file, c-à-d le nombre maximum d'éléments
     * qu'elle peut contenir.
     *
     * @return la capacité de la file.
     */
    @Override
    public int capacity() {
        return maxCapacity;
    }

    /**
     * Retourne la taille de la file, c-à-d le nombre d'éléments qu'elle
     * contient.
     *
     * @return la taille de la file.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Retourne vrai ssi la file est vide, c-à-d que sa taille est nulle.
     *
     * @return vrai ssi la file est vide.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Retourne vrai ssi la file est pleine, c-à-d que sa taille est égale à sa
     * capacité.
     *
     * @return vrai ssi la file est pleine.
     */
    @Override
    public boolean isFull() {
        return size == maxCapacity;
    }

    /**
     * Ajoute l'élément donné en fin de file.
     *
     * @param newElement l'élément à ajouter.
     * @throws IllegalStateException si la file est pleine.
     */
    @Override
    public void addLast(int newElement) {
        if (isFull())
            throw new IllegalStateException("Queue is full");

        queue[lastPointerIndex++] = newElement;
        size++;
        if (lastPointerIndex >= maxCapacity)
            lastPointerIndex = 0;
    }

    /**
     * Supprime et retourne l'élément en début de la file.
     *
     * @return l'élément en début de file.
     * @throws IllegalStateException si la file est vide.
     */
    @Override
    public int removeFirst() {
        if (isEmpty())
            throw new IllegalStateException("Queue is empty");

        int f = queue[headPointerIndex++];
        size--;

        if (headPointerIndex >= maxCapacity)
            headPointerIndex = 0;

        return f;
    }
}
