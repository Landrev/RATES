package com.github.landrev.rates.types.nullable;

public abstract class AbstractNullable<T> implements Nullable<T> {

    AbstractNullable() {}

    @Override
    public boolean isNotEmpty() {
        return !isEmpty();
    }

    @Override
    public T get() {
        return get(false);
    }
}
