package com.github.landrev.rates.http.server.handler;

import com.github.landrev.rates.types.priority.Priority;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public abstract class AbstractHttpHandler implements HttpHandler {

    private static final int DEFAULT_PRIORITY_VALUE = 0;

    @Override
    public Priority getPriority() {
        return Priority.create(DEFAULT_PRIORITY_VALUE);
    }

    @Override
    public final int compareTo(HttpHandler httpHandler) {
        return getPriority().compareTo(httpHandler.getPriority());
    }

    protected Optional<Byte> getQueryParamAsByte(ServerRequest request,
            String name,
            Function<RuntimeException, RuntimeException> exceptionMapper) {
        return getQueryParam(request, name, Byte::valueOf, exceptionMapper);
    }

    protected Optional<Short> getQueryParamAsShort(ServerRequest request,
            String name,
            Function<RuntimeException, RuntimeException> exceptionMapper) {
        return getQueryParam(request, name, Short::valueOf, exceptionMapper);
    }

    protected Optional<Integer> getQueryParamAsInt(ServerRequest request,
            String name,
            Function<RuntimeException, RuntimeException> exceptionMapper) {
        return getQueryParam(request, name, Integer::valueOf, exceptionMapper);
    }

    protected Optional<Long> getQueryParamAsLong(ServerRequest request,
            String name,
            Function<RuntimeException, RuntimeException> exceptionMapper) {
        return getQueryParam(request, name, Long::valueOf, exceptionMapper);
    }

    protected Optional<String> getQueryParamAsString(ServerRequest request, String name) {
        return getQueryParam(request, name);
    }

    protected <T extends Enum<T>> Optional<T> getQueryParamAsEnum(ServerRequest request,
            String name,
            Class<T> enumClass,
            Function<RuntimeException, RuntimeException> exceptionMapper) {
        return getQueryParam(request, name, $param -> Enum.valueOf(enumClass, $param), exceptionMapper);
    }

    protected Byte getPathParamAsByte(ServerRequest request,
            String name,
            Function<RuntimeException, RuntimeException> exceptionMapper) {
        return getPathParam(request, name, Byte::valueOf, exceptionMapper);
    }

    protected Short getPathParamAsShort(ServerRequest request,
            String name,
            Function<RuntimeException, RuntimeException> exceptionMapper) {
        return getPathParam(request, name, Short::valueOf, exceptionMapper);
    }

    protected Integer getPathParamAsInt(ServerRequest request,
            String name,
            Function<RuntimeException, RuntimeException> exceptionMapper) {
        return getPathParam(request, name, Integer::valueOf, exceptionMapper);
    }

    protected Long getPathParamAsLong(ServerRequest request,
            String name,
            Function<RuntimeException, RuntimeException> exceptionMapper) {
        return getPathParam(request, name, Long::valueOf, exceptionMapper);
    }

    protected String getPathParamAsString(ServerRequest request, String name) {
        return getPathParam(request, name);
    }

    protected <T> Optional<T> getQueryParam(ServerRequest request,
            String name,
            Function<String, T> valueMapper,
            Function<RuntimeException, RuntimeException> exceptionMapper) {
        return getQueryParam(request, name).map($param -> {
            try {
                return valueMapper.apply($param);
            } catch (RuntimeException e) {
                throw exceptionMapper.apply(e);
            }
        });
    }

    protected Optional<String> getFirstHeader(ServerRequest request, String name) {
        return Optional.ofNullable(request.headers().firstHeader(name));
    }

    protected List<String> getHeader(ServerRequest request, String name) {
        return request.headers().header(name);
    }

    protected <T> T getPathParam(ServerRequest request,
            String name,
            Function<String, T> valueMapper,
            Function<RuntimeException, RuntimeException> exceptionMapper) {
        try {
            return valueMapper.apply(getPathParam(request, name));
        } catch (RuntimeException e) {
            throw exceptionMapper.apply(e);
        }
    }

    private static Optional<String> getQueryParam(ServerRequest request, String name) {
        return request.queryParam(name);
    }

    private static String getPathParam(ServerRequest request, String name) {
        return request.pathVariable(name);
    }
}
