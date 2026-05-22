package dev.padjokej.week12;

import java.io.IOException;
import java.io.OutputStream;

public class BitsOutputStream implements AutoCloseable {
    private final OutputStream out;
    private int waitingBits;
    private int totalBitCount;

    public BitsOutputStream(OutputStream out) {
        this.out = out;
        this.waitingBits = 0;
    }

    public void writeU(int v0, int bitCount) throws IOException {
        if (bitCount < 0 || bitCount > 16) throw new IllegalArgumentException();

        int v = v0 & ((1 << bitCount) - 1);

        totalBitCount += bitCount;
        waitingBits <<= bitCount;
        waitingBits |= v;

        while (totalBitCount >= Byte.SIZE) {
            int writtenBits = (totalBitCount - Byte.SIZE);

            out.write(waitingBits >>> writtenBits);
            waitingBits &= (1 << writtenBits) - 1;

            totalBitCount -= 8;
        }
    }

    @Override
    public void close() throws IOException {
        if (totalBitCount > 0) {
            out.write(waitingBits << (Byte.SIZE - totalBitCount));

            waitingBits = (totalBitCount = 0);
        }

        out.close();
    }
}
