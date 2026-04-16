package dev.padjokej.midterm.year2024;

import dev.padjokej.week05.Image;

@FunctionalInterface
public interface Animation<T> {
    // ADAPTER
    static <T> Animation<T> ofImage(Image<T> image) {
        return (x, y, t) -> image.apply(x, y);
    }

    // DECORATOR
    default Animation<T> moving(double dx, double dy) {
        return (x, y, t) -> this.apply(x - t * dx, y - t * dy, t);
    }

    // ADAPTER
    default Image<T> snapshot(double t) {
        return (x, y) -> this.apply(x, y, t);
    }

    // COMPOSITE
    default Animation<T> alternatingWith(Animation<T> anim2) {
        return (x, y, t) -> {
            int intT = (int) t;
            return (t % 2 == 0) ? this.apply(x, y, t) : anim2.apply(x, y, t);
        };
    }

    T apply(double x, double y, double t);
}
