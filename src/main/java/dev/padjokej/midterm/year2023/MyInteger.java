package dev.padjokej.midterm.year2023;

public final class MyInteger {
    /*
    * - [ ] compress(i, i) == i
    * - [x] compress(i, m) == compress(i & m, m)
    * - [x] compress(i, -1) == i
    * - [ ] compress(i, -1 << n) == i >> n
    * - [x] compress(i, 1 << n) == (i >> n) & 1
    * */
    public static int compress(int i, int mask) {
        int current = 0;
        int index = 0;

        while (mask != 0) {
            if ((mask & 1) != 0) current |= (i & 1) << (index++);

            i >>>= 1;
            mask >>>= 1;
        }

        return current;
    }
}
