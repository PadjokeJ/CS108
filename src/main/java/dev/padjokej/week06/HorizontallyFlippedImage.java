package dev.padjokej.week06;

public record HorizontallyFlippedImage(TextImage original) implements TextImage {

    @Override
    public int width() {
        return original.width();
    }

    @Override
    public int height() {
        return original.height();
    }

    @Override
    public char charAt(int x, int y) {
        return original().charAt(width() - x - 1, y);
    }
}
