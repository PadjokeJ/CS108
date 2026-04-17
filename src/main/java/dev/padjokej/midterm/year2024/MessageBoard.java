package dev.padjokej.midterm.year2024;

import java.util.*;

public record MessageBoard(Map<PlayerColor, Integer> points, List<String> messages) {
    public enum PlayerColor { RED, BLUE, GREEN, YELLOW, PURPLE }

    public MessageBoard {
        points.forEach((_, p) -> { if (p <= 0) throw new IllegalArgumentException(); });

        points = Map.copyOf(points);
        messages = List.copyOf(messages);
    }

    public Set<PlayerColor> leaders() {
        HashSet<PlayerColor> leaderSet = new HashSet<>();
        int maxPoints = 0;

        for (Map.Entry<PlayerColor, Integer> entry : points.entrySet()) {
            int points = entry.getValue();
            if (points > maxPoints) {
                leaderSet.clear();
                leaderSet.add(entry.getKey());
                maxPoints = points;
            } else if (points == maxPoints) {
                leaderSet.add(entry.getKey());
            }
        }
        return leaderSet;
    }

    public static class Builder {
        Map<PlayerColor, Integer> points;
        List<String> messages;
        public Builder(MessageBoard messageBoard) {
            points = new HashMap<>(messageBoard.points());
            messages = new ArrayList<>(messageBoard.messages());
        }

        public Builder addMessage(String message) {
            messages.add(message);
            return this;
        }

        public Builder addMessage(String message, int points, Set<PlayerColor> players) {
            if (points <= 0) throw new IllegalArgumentException();
            if (players.isEmpty()) throw new IllegalArgumentException();


            players.forEach((player) -> this.points.merge(player, points, Integer::sum));

            return addMessage(message);
        }

        public MessageBoard build() {
            return new MessageBoard(points, messages);
        }
    }
}
