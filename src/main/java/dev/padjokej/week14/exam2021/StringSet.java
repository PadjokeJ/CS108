package dev.padjokej.week14.exam2021;

import java.util.Arrays;
import java.util.Iterator;
import java.util.StringJoiner;

public final class StringSet implements Iterable<String> {
    private final String[] elements;

    private StringSet(String[] elements) {
        this.elements = elements;
    }

    public static StringSet ofSortedArray(String[] elements) {
        String previous = null;

        for (String s : elements) {
            if (previous != null && previous.compareTo(s) >= 0)
                throw new IllegalArgumentException();

            previous = s;
        }

        return new StringSet(Arrays.copyOf(elements, elements.length));
    }

    public int size() {
        return elements.length;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean contains(String el) {
        return Arrays.binarySearch(elements, el) >= 0;
    }

    public StringSet union(StringSet other) {
        int size = other.size() + this.size();

        String[] elements = new String[size];

        int t = 0, o = 0;

        while (t < this.size() || o < other.size()) {
            if (this.elements[t].compareTo(other.elements[o]) < 0) {
                elements[o + t] = this.elements[t++];
            } else {
                elements[o + t] = other.elements[o++];
            }
        }

        return new StringSet(elements);
    }

    @Override
    public Iterator<String> iterator() {
        return new Iterator<>() {
            int i = 0;

            @Override
            public String next() {
                return elements[i++];
            }

            @Override
            public boolean hasNext() {
                return i < size();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(",", "[", "]");
        for(String s : this) {
            sj.add(s);
        }

        return sj.toString();
    }

    // Since this class is immutable, we should probably redefine
    @Override
    public int hashCode() {
        return Arrays.hashCode(elements);
    }

    @Override
    public boolean equals(Object other) {
        if (other != null && other instanceof StringSet otherSet)
            return Arrays.equals(this.elements, otherSet.elements);
        return false;
    }

//    public final class ComparableSet<T extends Comparable<T>> implements Iterable<T> {}

    public static class Builder {
        String[] elements = new String[10];
        int size = 0;

        public Builder add(String s) {
            if (elements.length < size + 1) {
                elements = Arrays.copyOf(elements, size * 2);
            }
            elements[size++] = s;

            return this;
        }

        public Builder compact() {
            Arrays.sort(elements, 0, size);

            String[] newElements = elements.clone();

            int i = 1, j = 1;

            while (i < elements.length) {
                if (elements[i].equals(elements[i - 1])) {
                    i++;
                }
                newElements[j++] = elements[i++];
            }

            size = j;

            while (j < newElements.length) {
                newElements[j++] = null;
            }

            this.elements = newElements;

            return this;
        }

        public StringSet build() {
            this.compact();

            return new StringSet(Arrays.copyOf(elements, size));
        }
    }
}
