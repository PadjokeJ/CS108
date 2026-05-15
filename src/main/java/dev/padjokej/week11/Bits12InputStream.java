package dev.padjokej.week11;

import java.io.IOException;
import java.io.InputStream;

/**
 * An input stream adapter, taking a standard stream of bytes and producing a
 * stream of 12-bits (unsigned) integers. Each group of three successive bytes
 * of the underlying stream is decoded as a pair of 12-bits unsigned integers.
 */
public final class Bits12InputStream implements AutoCloseable {
    private final InputStream in;
    private int waitingBits;

    /**
     * Creates a new 12-bits input stream on top of the given 8-bits input
     * stream.
     *
     * @param in
     *            the underlying 8-bits input stream
     */
    public Bits12InputStream(InputStream in) {
        this.in = in;
        this.waitingBits = -1;
    }

    /**
     * Read the next 12-bits value from this stream and return it as an integer
     * in the range from 0 to 4095, or return -1 if the end of the stream has
     * been reached.
     *
     * @return the next 12-bits value or -1 if the end has been reached
     * @throws IOException
     *             if an I/O error occurs
     */
    public int readU12() throws IOException {
        int b1 = in.read();
        if (waitingBits == -1) {
            if (b1 == -1)
                return -1;
            int b2 = in.read();
            if (b2 == -1)
                throw new IOException("truncated 12 bits stream");
            waitingBits = b2 & 0xF;
            return (b1 << 4) | (b2 >> 4);
        } else {
            if (b1 == -1) {
                if (waitingBits != 0)
                    throw new IOException("truncated 12 bits stream");
                else
                    return -1;
            }
            int bits12 = (waitingBits << 8) | b1;
            waitingBits = -1;
            return bits12;
        }
    }

    /* (non-Javadoc)
     * @see java.lang.AutoCloseable#close()
     */
    @Override
    public void close() throws IOException {
        in.close();
    }
}
