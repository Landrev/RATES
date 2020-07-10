package com.github.landrev.rates.types.value;

import com.github.landrev.rates.types.mergable.Mergable;

public interface ValueObject<T, W> extends Mergable<W> {

    T getValue();

    default W merge(W value) {
        return value;
    }
}
