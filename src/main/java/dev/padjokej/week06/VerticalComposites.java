package dev.padjokej.week06;

public record VerticalComposites(TextImage above, TextImage below) implements TextImage {
    @Override
    public int width() {
        return Math.max(above.width(), below.width());
    }

    @Override
    public int height() {
        return above.height() + below.height();
    }

    @Override
    public char charAt(int x, int y) {
        if (y < above.height()) {
            return above.charAt(x, y);
        }
        return below.charAt(x, y - above.height());
    }
}
