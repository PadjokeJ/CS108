package dev.padjokej.week06;

public record FilledImage(int width, int height, char c) implements TextImage {
    @Override
    public char charAt(int x, int y) {
        if (x < width && y < height)
            return c;
        return ' ';
    }
}
