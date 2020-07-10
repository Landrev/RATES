package com.github.landrev.rates.types.nullable;

import java.io.Serializable;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface Nullable<T> extends Serializable {

    <R> Nullable<R> map(Function<? super T, ? extends R> mappingFunction);

    <R> Nullable<R> flatMap(Function<? super T, ? extends Nullable<? extends R>> mappingFunction);

    <R> R useConditional(R object, BiFunction<R, T, R> action);

    <R> R useConditional(R object, BiFunction<R, T, R> action, Function<R, R> alternativeFunction);

    boolean isEmpty();

    boolean isNotEmpty();

    T get(boolean throwOnNull);

    T get();

    static <T> Nullable<T> of(T value) {
        if (value == null) {
            return new EmptyNullable<>();
        }

        return new NonEmptyNullable<>(value);
    }

    static <T> Nullable<T> empty() {
        return new EmptyNullable<>();
    }

    static <T> Nullable<T> of(Optional<? extends T> value) {
        if (value.isEmpty()) {
            return empty();
        }

        return of(value.get());
    }
}
