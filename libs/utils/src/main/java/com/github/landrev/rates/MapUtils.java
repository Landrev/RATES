package com.github.landrev.rates;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class MapUtils {

    private MapUtils() {}

    @SafeVarargs
    public static <K, V> Map<K, V> merge(Map<K, V>... maps) {
        return Stream.of(maps)
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static <K, V> MapBuilder<K, V> mapBuilder() {
        return new MapBuilder<>();
    }

    public static <K, V> MapBuilder<K, V> mapBuilder(Class<K> keyClass, Class<V> valueClass) {
        return new MapBuilder<>();
    }

    public static class MapBuilder<K, V> {

        private final Map<K, V> map = new HashMap<>();

        public MapBuilder<K, V> entry(K key, V value) {
            if (value == null) {
                return this;
            }

            map.put(key, value);
            return this;
        }

        public Map<K, V> build() {
            return map;
        }
    }
}
