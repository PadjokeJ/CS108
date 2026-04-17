package dev.padjokej.midterm.year2026;

import java.util.*;
import java.util.random.RandomGenerator;

public record LSystem(String string, Map<Character, List<Branch>> rules, Set<Character> lineChars, int turningAngle) {
    public record Branch (double probability, String expansion) {}

    public LSystem {
        lineChars = Set.copyOf(lineChars);
        Map<Character, List<Branch>> temp = new HashMap<>();

        for (Map.Entry<Character, List<Branch>> entry : rules.entrySet()) {
            temp.put(entry.getKey(), normalize(entry.getValue()));
        }

        rules = Map.copyOf(temp);
    }

    private static List<Branch> normalize(List<Branch> branches) {
        double total = 0;
        for (Branch b : branches) total += b.probability();

        List<Branch> temp = new ArrayList<>();
        for (Branch b : branches) temp.add(new Branch(b.probability() / total, b.expansion()));

        return List.copyOf(temp);
    }

    private String choose(List<Branch> branches, RandomGenerator random) {
        double total = 0;
        double c = random.nextDouble();

        for (Branch b : branches) {
            total += b.probability();
            if (c < total) return b.expansion();
        }

        return branches.getLast().expansion();
    }
}
