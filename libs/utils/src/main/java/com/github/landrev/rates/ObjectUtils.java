package com.github.landrev.rates;

import java.util.function.Function;
import java.util.function.Supplier;

public final class ObjectUtils {

    private ObjectUtils() {}

    public static <T> T optGet(T value, T defaultValue) {
        if (value == null) {
            return defaultValue;
        }

        return value;
    }

    public static <T> T optGet(T value, Supplier<T> defaultValueSupplier) {
        if (value == null) {
            return defaultValueSupplier.get();
        }

        return value;
    }

    public static <T, R> R optGet(T value, Function<T, R> resultProvider, R defaultResult) {
        if (value == null) {
            return defaultResult;
        }

        return resultProvider.apply(value);
    }

    public static <T, R> R optGet(T value, Function<T, R> resultProvider, Supplier<R> defaultResultSupplier) {
        if (value == null) {
            return defaultResultSupplier.get();
        }

        return resultProvider.apply(value);
    }
}
