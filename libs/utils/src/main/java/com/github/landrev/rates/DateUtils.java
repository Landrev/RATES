package com.github.landrev.rates;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public final class DateUtils {

    private static final String UTC_ZONE = "UTC";

    private DateUtils() {}

    public static LocalDateTime of(Instant instant) {
        return LocalDateTime.ofInstant(instant, ZoneId.of(UTC_ZONE));
    }

    public static Instant of(LocalDateTime localDateTime) {
        return localDateTime.toInstant(ZoneOffset.UTC);
    }
}
