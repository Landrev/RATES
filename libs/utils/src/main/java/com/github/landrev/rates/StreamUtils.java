package com.github.landrev.rates;

import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class StreamUtils {

    private StreamUtils() {}

    public static <T> Stream<T> asStream(Iterator<T> sourceIterator) {
        return asStream(sourceIterator, false);
    }

    public static <T> Stream<T> asStream(Iterator<T> sourceIterator, boolean parallel) {
        Iterable<T> iterable = () -> sourceIterator;
        return StreamSupport.stream(iterable.spliterator(), parallel);
    }
}