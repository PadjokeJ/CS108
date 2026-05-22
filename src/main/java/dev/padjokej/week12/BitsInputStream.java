package dev.padjokej.week12;

import java.io.IOException;
import java.io.InputStream;

public class BitsInputStream implements AutoCloseable {
    private final InputStream in;
    private int waitingBits;
    private int totalBitCount;

    public BitsInputStream(InputStream in) {
        this.in = in;
        waitingBits = 0;
        totalBitCount = 0;
    }

    public int read(int bitCount) throws IOException {
        if (bitCount < 0 || bitCount > 16) throw new IllegalArgumentException();

        while (totalBitCount < bitCount) {
            int b1 = in.read();
            if (b1 == -1) {
                int r = -1;
                if (totalBitCount != 0) {
                    r = waitingBits << (bitCount - totalBitCount);
                    waitingBits = (totalBitCount = 0);
                }
                return r;
            }

            waitingBits <<= Byte.SIZE;
            totalBitCount += Byte.SIZE;

            waitingBits |= b1;
        }

        int writtenBits = (totalBitCount - bitCount);

        int v = waitingBits >>> writtenBits;
        totalBitCount -= bitCount;
        waitingBits &= (1 << writtenBits) - 1;

        return v;
    }

    @Override
    public void close() throws IOException {
        in.close();
    }
}
