package com.github.landrev.rates.types.pair;

public class Pair<T1, T2> {

    private final T1 first;

    private final T2 second;

    public static <T1, T2> Pair<T1, T2> create(T1 first, T2 second) {
        return new Pair<>(first, second);
    }

    public Pair(final T1 first, final T2 second) {
        this.first = first;
        this.second = second;
    }

    public T1 getFirst() {
        return first;
    }

    public T2 getSecond() {
        return second;
    }
}
