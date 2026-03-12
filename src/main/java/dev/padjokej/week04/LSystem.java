package dev.padjokej.week04;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public record LSystem(String string, Map<Character, String> rules, Set<Character> lineChars, int turningAngle) {
    public LSystem {
        rules = Map.copyOf(rules);
        lineChars = Set.copyOf(lineChars);
    }

    public LSystem(String s, Map<Character, String> rule, String lineString, int turningAngle) {
        this(s, rule, setFromString(lineString), turningAngle);
    }

    public LSystem evolve() {
        int l = string.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < l; i++) {
            Character c = string.charAt(i);
            sb.append(rules.getOrDefault(c, c.toString()));
        }

        return new LSystem(sb.toString(), rules, lineChars, turningAngle);
    }
    public LSystem evolve(int steps) {
        var lsys = this;
        for (int i = 0; i < steps; i++) {
            lsys = lsys.evolve();
        }
        return lsys;
    }

    private static Set<Character> setFromString(String s) {
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            set.add(s.charAt(i));
        }
        return set;
    }

}
