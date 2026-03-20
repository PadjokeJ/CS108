package dev.padjokej.week05;

@FunctionalInterface
public interface Image<C> {
    C apply(double x, double y);

    public static final Image<ColorRGB> RED_DISK = (x, y) ->
            Math.sqrt(x * x + y * y) <= 1d ? ColorRGB.RED : ColorRGB.WHITE;

    public static final Image<Double> HORIZONTAL_GRADIENT_MASK = (x, y) ->
            x < -1 ? 0 : x > 1 ? 1 : (x + 1) / 2;

    public static <T> Image<T> chessboard(T c1, T c2, double w) {
        return (x, y) -> {
            int xi = (int) Math.floor(x / w);
            int yi = (int) Math.floor(y / w);

            return (xi + yi) % 2 == 0 ? c1 : c2;
        };
    }

    public static Image<ColorRGB> composed(Image<ColorRGB> im1, Image<ColorRGB> im2, Image<Double> mask) {
        return (x, y) -> im2.apply(x, y).mixWith(im1.apply(x, y), mask.apply(x, y));
    }

    public static Image<Double> mandelbrot(int max) {
        return (x, y) -> {
            int i = 0;
            Complex c = Complex.ZERO;
            Complex xy = new Complex(x, y);

            while (++i < max) {
                c = Complex.add(Complex.square(c), xy);

                if (c.module() >= 2) break;
            }
            
            return (((double) i)) / (((double) max));
        };
    }

    default Image<C> rotated(double rads) {
        return (x, y) -> {
            double cosA = Math.cos(rads);
            double sinA = Math.sin(rads);

            return this.apply(x * cosA + y * sinA, y * cosA - x * sinA);
        };
    }
}
