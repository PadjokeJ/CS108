package dev.padjokej.week06;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class Main {
    public static final String PATH = "src/main/resources/";

    public static double log_2(double x) {
        return Math.log(x) / Math.log(2.);
    }

    public static int sequenceLength(int[] freq) {
        int l = 0;
        for (int j : freq) {
            l += j;
        }
        return l;
    }

    public static double probability(int element, int sampleSize) {
        return element / (double) sampleSize;
    }

    public static void stemLeaf(int[] freq) {
        int n = 0;
        for (int i : freq) {
            n = Math.max(n, i);
        }
        System.out.println("n : " + n);
        int divisor = Math.max(n / 20, 1);

        for (int i = 0; i <= 25; i++) {
            System.out.printf("%2d|", i);
            for (int j = 0; j < 10; j++) {
                if (i * 10 + j >= 256) break;

                System.out.print(Integer.toString(j).repeat(freq[i * 10 + j] / divisor));
            }
            System.out.println();
        }
    }

    public static double entropy(int[] freq) {
        int length = sequenceLength(freq);

        double h = 0;

        for (int i : freq) {
            double p_i = probability(i, length);
            if (p_i != 0)
                h -= p_i * log_2(p_i);
        }

        return h;
    }

    public static double average(int[] freq) {
        int max = freq.length;
        int n = 0;
        for (int i = 0; i < max; i++) {
            n += i * freq[i];
        }

        double l = sequenceLength(freq);

        return n / l;
    }

    public static int[] byteFrequencies(String filename) throws IOException {
        InputStream stream = new FileInputStream(filename);

        int[] freq = new int[256];
        int b = 0;

        while ((b = stream.read()) != -1) freq[b] += 1;

        stream.close();

        return freq;
    }

    public static void main(String[] args) {
        try {
            for (int i = 0; i < 6; i++) {
                var freq = byteFrequencies(PATH + "file" + i + ".bin");
                System.out.println(Arrays.toString(freq));
                System.out.println(average(freq));
                System.out.println(entropy(freq));
                stemLeaf(freq);
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
}
