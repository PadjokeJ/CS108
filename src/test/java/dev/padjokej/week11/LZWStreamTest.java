package dev.padjokej.week11;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Random;

import dev.padjokej.week11.Bits12OutputStream;
import org.junit.jupiter.api.Test;

public class LZWStreamTest {
    private OutputStream newLZWOutputStream(OutputStream underlyingStream) {
        return new LZWOutputStream(underlyingStream);
    }

    private InputStream newLZWInputStream(InputStream underlyingStream) {
        return new LZWInputStream(underlyingStream); // FIXME: return a concrete LZW input stream
    }

    @Test
    public void outputStreamCorrectlyCompressesEmptyStream() throws IOException {
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        try (OutputStream lzws = newLZWOutputStream(bs)) {
            // do not write anything...
        }
        assertArrayEquals(new byte[0], bs.toByteArray());
    }

    @Test
    public void outputStreamCorrectlyCompressesSequence() throws IOException {
        ByteArrayOutputStream ebs = new ByteArrayOutputStream();
        ByteArrayOutputStream abs = new ByteArrayOutputStream();

        try (Bits12OutputStream eb12s = new Bits12OutputStream(ebs);
             OutputStream lzws = newLZWOutputStream(abs)) {
            for (int b = 0; b <= 255; ++b) {
                lzws.write(b);
                eb12s.writeU12(b);
            }
        }
        System.out.println(Arrays.toString(ebs.toByteArray()));
        System.out.println(Arrays.toString(abs.toByteArray()));
        assertArrayEquals(ebs.toByteArray(), abs.toByteArray());
    }

    @Test
    public void outputStreamCorrectlyCompressesRepeatingStream() throws IOException {
        byte[] inputBytes = new byte[20];
        Arrays.fill(inputBytes, (byte)42);
        byte[] expectedBytes = new byte[] { 2, -95, 0, 16, 17, 2, 16, 49, 3 };
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        try (OutputStream lzws = newLZWOutputStream(bs)) {
            lzws.write(inputBytes);
        }
        assertArrayEquals(expectedBytes, bs.toByteArray());
    }

    @Test
    public void inputStreamCorrectlyDecompressesRepeatingStream() throws IOException {
        byte[] expectedBytes = new byte[20];
        Arrays.fill(expectedBytes, (byte)42);
        byte[] compressedBytes = new byte[] { 2, -95, 0, 16, 17, 2, 16, 49, 3 };
        ByteArrayInputStream bs = new ByteArrayInputStream(compressedBytes);
        ByteArrayOutputStream as = new ByteArrayOutputStream();
        try (InputStream lzws = newLZWInputStream(bs)) {
            int b;
            while ((b = lzws.read()) != -1)
                as.write(b);
        }
        assertArrayEquals(expectedBytes, as.toByteArray());
    }

    @Test
    public void inputStreamIsInverseOfOutputStream() throws IOException {
        byte[] inputBytes = new byte[20000];
        Random r = new Random(2015);
        r.nextBytes(inputBytes);
        ByteArrayOutputStream cbs = new ByteArrayOutputStream();
        try (OutputStream os = newLZWOutputStream(cbs)) {
            os.write(inputBytes);
        }
        ByteArrayOutputStream ubs = new ByteArrayOutputStream();
        try (InputStream is =
                newLZWInputStream(new ByteArrayInputStream(cbs.toByteArray()))) {
            int b;
            while ((b = is.read()) != -1)
                ubs.write(b);
        }
        assertArrayEquals(inputBytes, ubs.toByteArray());
    }
}
