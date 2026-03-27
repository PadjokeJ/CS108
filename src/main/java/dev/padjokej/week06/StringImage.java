package dev.padjokej.week06;

public class StringImage implements TextImage {
    static final int HEIGHT = 1;
    final int width;
    String string;

    public StringImage(String s) {
        width = s.length();
        string = s;
    }

    @Override
    public int width() {
        return width;
    }

    @Override
    public int height() {
        return HEIGHT;
    }

    @Override
    public char charAt(int x, int y) {
        if (x < width && y == 0)
            return string.charAt(x);
        return ' ';
    }
}
