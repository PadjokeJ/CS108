package dev.padjokej.week06;

public record Transposed(TextImage original) implements TextImage {
    @Override
    public int width() {
        return original().height();
    }

    @Override
    public int height() {
        return original().width();
    }

    @Override
    public char charAt(int x, int y) {
        return original().charAt(y, x);
    }
}
