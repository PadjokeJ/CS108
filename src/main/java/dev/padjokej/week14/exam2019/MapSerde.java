package dev.padjokej.week14.exam2019;

import java.util.Map;

public class MapSerde<K, V> implements AsciiSerde<Map<K, V>> {
    final AsciiSerde<K> keySerde;
    final AsciiSerde<V> valueSerde;

    public MapSerde(AsciiSerde<K> keySerde, AsciiSerde<V> valueSerde, char separator) {
        this.keySerde = keySerde;
        this.valueSerde = valueSerde;

        if (!AsciiSerde.isAscii(separator)
                || keySerde.alphabet().contains(separator) || valueSerde.alphabet().contains(separator))
            throw new IllegalArgumentException();


    }
}
