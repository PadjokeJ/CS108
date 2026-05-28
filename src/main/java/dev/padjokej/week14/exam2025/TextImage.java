package dev.padjokej.week14.exam2025;

public interface TextImage {
    char charAt(int row, int column);

    static TextImage rectangle(int w, int h) {
        return (r, c) -> {
            if (w < 2 || h < 2)
                throw new IllegalArgumentException();

            boolean horBorder = r == 0 || r == h - 1;
            boolean verBorder = c == 0 || c == w - 1;

            if (horBorder && verBorder)
                return '+';
            if (horBorder)
                return '-';
            if (verBorder)
                return '|';
            return ' ';
        };
    }

    default TextImage over(TextImage other) {
        return (r, c) -> {
            char tc = this.charAt(r, c);
            if (tc == ' ')
                return other.charAt(r, c);
            return tc;
        };
    }

    default TextImage translated(int dr, int dc) {
        return (r, c) ->
            charAt(r - dr, c - dc);
    }
}
