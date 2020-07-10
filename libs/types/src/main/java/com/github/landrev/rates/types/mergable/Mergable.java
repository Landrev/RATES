package com.github.landrev.rates.types.mergable;

public interface Mergable<T> {

    T merge(T value);
}
