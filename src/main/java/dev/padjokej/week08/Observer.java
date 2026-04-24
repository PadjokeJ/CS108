package dev.padjokej.week08;

@FunctionalInterface
public interface Observer {
    void update(Subject subject);
}
