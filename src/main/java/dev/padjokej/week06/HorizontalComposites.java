package dev.padjokej.week06;

public record HorizontalComposites(TextImage left, TextImage right) implements TextImage {
    @Override
    public int width() {
        return left.width() + right.width();
    }

    @Override
    public int height() {
        return Math.max(left.height(), right.height());
    }

    @Override
    public char charAt(int x, int y) {
        if (x < left.width()) {
            return left.charAt(x, y);
        }
        return right().charAt(x - left().width(), y);
    }
}
