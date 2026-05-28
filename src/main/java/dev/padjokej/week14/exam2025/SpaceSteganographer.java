package dev.padjokej.week14.exam2025;

import java.util.List;

public class SpaceSteganographer {
    private SpaceSteganographer() {}

    private static final List<Character> SPACES =
            List.of('\u0020', '\u00A0', '\u2002', '\u2003');

    public static String encode(String substrate, byte[] message) {
        String[] segments = substrate.split(" ");
        StringBuilder sb = new StringBuilder();

        if (message.length > segments.length * 4)
            throw new IllegalArgumentException();

        for (int i = 0; i < message.length * 4; i++) {
            sb.append(segments[i]);
            sb.append(SPACES.get((message[i / 4] >>> ((i % 4) * 2)) & 3));
        }

        return sb.toString();
    }
}
