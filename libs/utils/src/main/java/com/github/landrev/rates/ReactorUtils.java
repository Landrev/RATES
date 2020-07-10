package com.github.landrev.rates;

import io.vavr.Function2;
import io.vavr.Function3;
import reactor.core.publisher.Mono;

public final class ReactorUtils {

    private ReactorUtils() {}

    public static <T1, T2, R> Mono<R> zip(
            Mono<T1> mono1,
            Mono<T2> mono2,
            Function2<T1, T2, R> zipFunction) {
        return mono1.zipWith(mono2, zipFunction);
    }

    public static <T1, T2, T3, R> Mono<R> zip(
            Mono<T1> mono1,
            Mono<T2> mono2,
            Mono<T3> mono3,
            Function3<T1, T2, T3, R> zipFunction) {
        return mono1.flatMap($value1 -> mono2.flatMap($value2 -> mono3.map($value3 ->
                zipFunction.apply($value1, $value2, $value3)))
        );
    }
}
