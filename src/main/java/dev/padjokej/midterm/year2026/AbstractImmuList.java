package dev.padjokej.midterm.year2026;

import java.util.StringJoiner;

public abstract class AbstractImmuList<E> implements ImmuList<E> {
    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(",", "[", "]");

        for (int i = 0; i < size(); i++) sj.add(get(i).toString());

        return sj.toString();
    }
}
