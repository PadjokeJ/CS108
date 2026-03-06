package dev.padjokej.week03;

import java.util.*;

public final class BurrowsWheelerTransform {
    private BurrowsWheelerTransform() {}

    public static Pair<Integer, String> forward(String s) {
        if (s == null || s.isEmpty())
            throw new IllegalArgumentException();

        ArrayList<String> strings = new ArrayList<>();

        for (int i = 0; i < s.length(); i++) {
            Queue<Character> q = stringToCharQueue(s);
            q = rotate(q, i);

            strings.add(charQueueToString(q));
        }

        Collections.sort(strings);

        int i = strings.indexOf(s);

        StringBuilder sb = new StringBuilder();
        for (String string : strings) {
            sb.append(string.charAt(string.length() - 1));
        }

        return new Pair<>(i, sb.toString());
    }

    public static String backward(Pair<Integer, String> p) {
        String s = p.second();
        int size = s.length();
        if (p.first() > size)
            throw new IndexOutOfBoundsException();

        ArrayList<String> al = new ArrayList<String>(size);

        for (int i = 0; i < size; i++) {
            al.add("");
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                al.set(j, s.charAt(j) + al.get(j));
            }
            Collections.sort(al);
        }

        return al.get(p.first());
    }

    private static String charQueueToString(Queue<Character> queue) {
        if (queue == null || queue.isEmpty())
            throw new IllegalArgumentException();

        StringBuilder sb = new StringBuilder();
        while (queue.peek() != null)
            sb.append(queue.poll());

        return sb.toString();
    }

    private static Queue<Character> stringToCharQueue(String string) {
        if (string == null || string.isEmpty())
            throw new IllegalArgumentException();

        ArrayDeque<Character> queue = new ArrayDeque<>();

        for (int i = 0; i < string.length(); i++) {
            queue.addLast(string.charAt(i));
        }

        return queue;
    }

    private static Queue<Character> rotate(Queue<Character> queue, int times) {
        if (queue instanceof Deque<Character> deque) {
            for (int i = 0; i < times; i++) {
                deque.addFirst(deque.pollLast());
            }
            return deque;
        }

        ArrayDeque<Character> q = new ArrayDeque<>();
        
        q.addFirst(queue.poll());
        while (queue.peek() != null) {
            q.addLast(queue.poll());
        }

        return q;
    }

    public static void main(String[] args) {
        System.out.println(backward(new Pair<>(17, "ssssrs;àtsessten .hmmfffm asnsltsLlllssrlhhhrrr   cl lmmb ll aaii  eaaouoeçstu uuueeeeeeee suuu ennu ceeeeeeeo a")));
    }
}
