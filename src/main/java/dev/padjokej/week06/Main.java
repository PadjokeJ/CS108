package dev.padjokej.week06;

public final class Main {
    static void main(String[] args) {
        TextImage blackSquare = TextImage.filled(3, 2, '#');
        TextImage whiteSquare = TextImage.filled(3, 2, ' ');

        TextImage twoByTwo = blackSquare.leftOf(whiteSquare).above(whiteSquare.leftOf(blackSquare));

        TextImage halfLine = twoByTwo.leftOf(twoByTwo);
        TextImage line = halfLine.leftOf(halfLine);

        TextImage halfHeight = line.above(line);
        TextImage full = halfHeight.above(halfHeight);

        full.framed().printOn(System.out);
    }
}
