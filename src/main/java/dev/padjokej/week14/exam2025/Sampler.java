package dev.padjokej.week14.exam2025;

import java.util.Arrays;
import java.util.List;
import java.util.random.RandomGenerator;

public class Sampler<T> {
    private final RandomGenerator rng;
    private int count;
    private final T[] samples;

    public Sampler(RandomGenerator rng, int size) {
        if (size < 0)
            throw new IllegalArgumentException();

        this.samples = (T[]) new Object[size];
        this.rng = rng;
    }

    public void accept(T value) {
        if (count < samples.length)
            samples[count++] = value;
        else {
            int i = rng.nextInt(count++);

            if (i < samples.length)
                samples[i] = value;
        }
    }

    public List<T> samples() {
        if (count <= samples.length) {
            T[] temp = Arrays.copyOf(samples, count);
            return List.of(temp);
        }
        return List.of(samples);
    }
}
