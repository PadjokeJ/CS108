package dev.padjokej.week11;

import java.io.IOException;
import java.io.OutputStream;

/**
 * An output stream adapter, taking a standard stream of bytes and producing a
 * stream of 12-bits (unsigned) integers. Each pair of 12-bits integers is
 * written as three successive bytes in the underlying stream.
 *
 * If an odd number of 12-bits integers are written, four 0 bits are inserted at
 * the end, for padding.
 */
public final class Bits12OutputStream implements AutoCloseable {
    private final OutputStream out;
    private int waitingBits;

    /**
     * Creates a new 12-bits output stream on top of the given 8-bits output
     * stream.
     *
     * @param out
     *            the underlying 8-bits output stream.
     */
    public Bits12OutputStream(OutputStream out) {
        this.out = out;
        this.waitingBits = -1;
    }

    /**
     * Write the given 12-bits value to this output stream.
     *
     * @param v
     *            the 12-bits unsigned value to write.
     * @throws IOException
     *             if an I/O error occurs
     * @throws IllegalArgumentException
     *             if the value does not fit in 12 bits (i.e. is outside the
     *             range 0 to 4095, included)
     */
    public void writeU12(int v) throws IOException {
        if (! (0 <= v && v <= 0xFFF))
            throw new IllegalArgumentException();

        if (waitingBits == -1) {
            out.write(v >> 4);
            waitingBits = v & 0xF;
        } else {
            out.write((waitingBits << 4) | (v >> 8));
            out.write(v & 0xFF);
            waitingBits = -1;
        }
    }

    /* (non-Javadoc)
     * @see java.lang.AutoCloseable#close()
     */
    @Override
    public void close() throws IOException {
        if (waitingBits != -1) {
            out.write(waitingBits << 4);
            waitingBits = -1;
        }
        out.close();
    }
}
