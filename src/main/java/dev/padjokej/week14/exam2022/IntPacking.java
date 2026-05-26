package dev.padjokej.week14.exam2022;

public final class IntPacking {
    private final int[] sizes;

    public IntPacking(int... sizes) {
        if (sizes.length < 2) throw new IllegalArgumentException();

        int i = 0;
        for (int s : sizes) {
            i += s;
            if (s > 32 || i > 32 || i <= 0)
                throw new IllegalArgumentException();
        }

        this.sizes = sizes.clone();
    }

    public int pack(int... values) {
        int total = 0;
        for (int s : sizes) total += s;

        int packed = 0;

        int i =  0;

        for (int v : values) {
            int s = sizes[i++];
            if ((((1 << s) - 1) & v) != v)
                throw new IllegalArgumentException();

            total -= s;
            packed |= v << total;
        }

        return packed;
    }

    public int unpack(int packedValue, int index) {
        if (index >= sizes.length || index < 0) throw new IndexOutOfBoundsException();

        int bitShift = 0;
        for (int i = index + 1; i < sizes.length; i++) {
            bitShift += sizes[i];
        }

        return (packedValue >>> bitShift) & ((1 << sizes[index]) - 1);
    }
}
