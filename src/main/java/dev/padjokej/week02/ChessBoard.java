package dev.padjokej.week02;

public class ChessBoard<C> implements Image<C> {
    public static final Image<ColorRGB> IMAGE = new ChessBoard<ColorRGB>(0.5f, ColorRGB.BLACK, ColorRGB.WHITE);
    private final float width;
    private final C col1, col2;

    public ChessBoard(float width, C col1, C col2) {
        if (width <= 0)
            throw new IllegalArgumentException("Width parameter must be strictly positive");
        this.width = width;

        this.col1 = col1;
        this.col2 = col2;
    }

    @Override
    public C apply(double x, double y) {
        int xi = (int) Math.floor(x / width);
        int yi = (int) Math.floor(y / width);

        return (xi + yi) % 2 == 0 ? col1 : col2;
    }
}
