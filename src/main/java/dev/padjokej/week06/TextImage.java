package dev.padjokej.week06;

import java.io.PrintStream;

public interface TextImage {
    public static TextImage fromString(String string) {
        return new StringImage(string);
    }

    public static FilledImage filled(int width, int height, char c) {
        return new FilledImage(width, height, c);
    }

    int width();

    int height();

    char charAt(int x, int y);

    default void printOn(PrintStream stream) {
        for (var y = 0; y < height(); y += 1) {
            for (var x = 0; x < width(); x += 1)
                stream.print(charAt(x, y));
            stream.println();
        }
    }

    default TextImage flippedHorizontally() {
        return new HorizontallyFlippedImage(this);
    }

    default TextImage transposed() {
        return new Transposed(this);
    }

    default TextImage leftOf(TextImage right) {
        return new HorizontalComposites(this, right);
    }

    default TextImage above(TextImage below) {
        return new VerticalComposites(this, below);
    }

    default TextImage framed() {
        TextImage inside = this;
        TextImage corner = TextImage.fromString("+");
        TextImage horizontalSeparator = TextImage.filled(inside.width(), 1, '-');
        TextImage verticalSeparator = TextImage.filled(1, inside.height(), '|');

        TextImage horizontalFrame = corner.leftOf(horizontalSeparator.leftOf(corner));

        TextImage sandwichedImage = verticalSeparator.leftOf(inside.leftOf(verticalSeparator));

        return horizontalFrame.above(sandwichedImage).above(horizontalFrame);
    }
}
