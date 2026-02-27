package dev.padjokej.week02;

public class HorizontalGradientMask implements Image<Double> {

    public double horizontalGradient(double x) {
        return x < -1 ? 0 : x > 1 ? 1 : (x + 1) / 2;
    }

    @Override
    public Double apply(double x, double y) {
        return horizontalGradient(x);
    }
}
