package dev.padjokej.week04;

import static java.lang.Math.cos;
import static java.lang.Math.floorMod;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

public record Turtle(double x, double y, int theta) {
    public Turtle() {
        this(0, 0, 0);
    }

    public Turtle turned(int angle) {
        return new Turtle(x, y, floorMod(theta + angle, 360));
    }

    public Turtle moved(double distance) {
        double x1 = x + distance * cos(toRadians(theta));
        double y1 = y + distance * sin(toRadians(theta));
        return new Turtle(x1, y1, theta);
    }
}
