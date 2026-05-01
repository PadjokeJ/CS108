package dev.padjokej.week09;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class Main {
    public static final String PATH = "src/main/resources/";
    public static final int MAX_STEM_LENGTH = 80;

    public static double log_2(double x) {
        return Math.log(x) / Math.log(2.);
    }

    public static int maxStemLeafLineLength(int[] freq) {
        int max = 0;

        int l = 0, i = 0;
        for (int f : freq) {
            if (i >= 10) {
                i = 0;
                max = Math.max(max, l);
                l = 0;
            }
            l += f;

            i++;
        }

        return max;
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
        int maxLength = maxStemLeafLineLength(freq);
        double divisor = Math.max((double) maxLength / MAX_STEM_LENGTH, 1);
        if (maxLength < MAX_STEM_LENGTH) divisor = 1;

        for (int i = 0; i <= 25; i++) {
            System.out.printf("%2d|", i);
            for (int j = 0; j < 10; j++) {
                if (i * 10 + j >= 256) break;

                System.out.print(Integer.toString(j).repeat((int) (freq[i * 10 + j] / divisor)));
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
        int n = 0, l = 0;
        for (int i = 0; i < max; i++) {
            int c = freq[i];
            n += i * c;

            l += c;
        }

        return (double) n / l;
    }

    public static int[] byteFrequencies(String filename) {
        int[] freq = new int[256];

        try (InputStream stream = new FileInputStream(filename)) {
            int b;
            while ((b = stream.read()) != -1) freq[b]++;
        } catch (IOException _) {}

        return freq;
    }

    static void main() {
        System.out.println("\n# Exercice 1\n");
        System.out.println(Arrays.toString(byteFrequencies(PATH + "file0.bin")));

        for (int i = 0; i < 6; i++) {
            var freq = byteFrequencies(PATH + "file" + i + ".bin");
            System.out.println(average(freq));
        }

        System.out.println("\n# Exercice 2\n");

        for (int i = 0; i < 6; i++) {
            var freq = byteFrequencies(PATH + "file" + i + ".bin");
            System.out.println(entropy(freq));
        }

        System.out.println("\n# Exercice 3\n");

        for (int i = 0; i < 6; i++) {
            var freq = byteFrequencies(PATH + "file" + i + ".bin");
            stemLeaf(freq);
            System.out.println();
        }
    }
}
