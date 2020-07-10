package com.github.landrev.rates.types.nullable;

import java.util.function.BiFunction;
import java.util.function.Function;

public final class EmptyNullable<T> extends AbstractNullable<T> {

    EmptyNullable() {}

    @Override
    public <R> Nullable<R> map(Function<? super T, ? extends R> mappingFunction) {
        return new EmptyNullable<>();
    }

    @Override
    public <R> Nullable<R> flatMap(Function<? super T, ? extends Nullable<? extends R>> mappingFunction) {
        return new EmptyNullable<>();
    }

    @Override
    public <R> R useConditional(R object, BiFunction<R, T, R> action) {
        return object;
    }

    @Override
    public <R> R useConditional(R object, BiFunction<R, T, R> action, Function<R, R> alternativeFunction) {
        return alternativeFunction.apply(object);
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public T get(boolean throwOnNull) {
        if(throwOnNull) {
            throw new NullPointerException("Empty nullable.");
        }

        return null;
    }
}
