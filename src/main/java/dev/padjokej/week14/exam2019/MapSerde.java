package dev.padjokej.week14.exam2019;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapSerde<K, V> implements AsciiSerde<Map<K, V>> {
    final AsciiSerde<K> keySerde;
    final AsciiSerde<V> valueSerde;

    final char sep;

    public MapSerde(AsciiSerde<K> keySerde, AsciiSerde<V> valueSerde, char separator) {
        this.keySerde = keySerde;
        this.valueSerde = valueSerde;

        if (!AsciiSerde.isAscii(separator)
                || keySerde.alphabet().contains(separator) || valueSerde.alphabet().contains(separator))
            throw new IllegalArgumentException();

        sep = separator;
    }

    @Override
    public String serialize(Map<K, V> value) {
        StringJoiner sj = new StringJoiner(String.valueOf(sep));

        value.entrySet().forEach(e -> {
            sj.add(keySerde.serialize(e.getKey()));
            sj.add(valueSerde.serialize(e.getValue()));
        });

        return sj.toString();
    }

    @Override
    public Map<K, V> deserialize(String s) {
        String[] split = s.split(String.valueOf(sep));

        if (split.length % 2 != 0) throw new IllegalArgumentException();

        Map<K, V> d = new HashMap<>();

        for (int i = 0; i < split.length; i += 2) {
            d.put(keySerde.deserialize(split[i]), valueSerde.deserialize(split[i + 1]));
        }

        return d;
    }

    @Override
    public Set<Character> alphabet() {
        return Stream.concat(keySerde.alphabet().stream(), valueSerde.alphabet().stream())
                .collect(Collectors.toSet());
    }
}
