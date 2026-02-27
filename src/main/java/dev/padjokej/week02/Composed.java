package dev.padjokej.week02;

public class Composed implements Image<ColorRGB> {
    private final Image<ColorRGB> foreground, background;
    private final Image<Double> mask;

    public Composed(Image<ColorRGB> im1, Image<ColorRGB> im2, Image<Double> mask) {
        foreground = im1;
        background = im2;
        this.mask = mask;
    }

    @Override
    public ColorRGB apply(double x, double y) {
        return background.apply(x, y).mixWith(foreground.apply(x, y), mask.apply(x, y));
    }
}
