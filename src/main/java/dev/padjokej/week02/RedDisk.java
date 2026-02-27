package dev.padjokej.week02;

import static java.lang.Math.sqrt;

public final class RedDisk<C> implements Image<C> {
    public static final Image<ColorRGB> IMAGE = new RedDisk<>(ColorRGB.RED, ColorRGB.WHITE);

    private final C foreground, background;
    public RedDisk(C color, C color2) {
        this.foreground = color;
        this.background = color2;
    }

    @Override
    public C apply(double x, double y) {
        double r = sqrt(x * x + y * y);
        return r <= 1d ? foreground : background;
    }
}