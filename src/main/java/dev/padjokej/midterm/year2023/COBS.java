package dev.padjokej.midterm.year2023;

import java.util.ArrayList;
import java.util.List;

public final class COBS {
    private COBS() {}

    public static List<Byte> stuff(List<Byte> bytes) {
        List<Byte> block = new ArrayList<>();
        List<Byte> stuff = new ArrayList<>();

        for (byte b : bytes) {
            if (b != 0 && block.size() < 255) block.add(b);
            if (b == 0 || block.size() >= 254) {
                stuff.add((byte) (block.size() + 1));
                stuff.addAll(block);
                block.clear();
            }
        }
        stuff.add((byte) (block.size() + 1));
        stuff.addAll(block);

        return stuff;
    }
}
