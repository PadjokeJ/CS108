package dev.padjokej.week02;

public final class WidthHalfer<C> implements Image<C> {
    private final Image<C> image;

    public WidthHalfer(Image<C> im) {
        image = im;
    }

    @Override
    public C apply(double x, double y) {
        return image.apply(x * 2, y);
    }
}
