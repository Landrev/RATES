package com.github.landrev.rates.types.nullable;

import java.util.function.BiFunction;
import java.util.function.Function;

public final class NonEmptyNullable<T> extends AbstractNullable<T> {

    private final T value;

    NonEmptyNullable(T value) {
        this.value = value;
    }

    @Override
    public <R> Nullable<R> map(Function<? super T, ? extends R> mappingFunction) {
        return new NonEmptyNullable<>(mappingFunction.apply(value));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R> Nullable<R> flatMap(Function<? super T, ? extends Nullable<? extends R>> mappingFunction) {
        return (Nullable<R>) mappingFunction.apply(value);
    }

    @Override
    public <R> R useConditional(R object, BiFunction<R, T, R> action) {
        return action.apply(object, value);
    }

    @Override
    public <R> R useConditional(R object, BiFunction<R, T, R> action, Function<R, R> alternativeFunction) {
        return action.apply(object, value);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public T get(boolean throwOnNull) {
        return value;
    }
}
