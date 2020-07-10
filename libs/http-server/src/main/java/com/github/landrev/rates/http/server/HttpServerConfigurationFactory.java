package com.github.landrev.rates.http.server;

import com.github.landrev.rates.http.server.handler.HttpHandler;
import com.github.landrev.rates.http.server.request.RequestContextFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.core.Ordered.LOWEST_PRECEDENCE;

public final class HttpServerConfigurationFactory {

    private static final Logger LOG = LoggerFactory.getLogger(HttpServerConfigurationFactory.class);

    private HttpServerConfigurationFactory() {}

    public static RouterFunction<ServerResponse> createHttpServerRouterFunction(Map<String, HttpHandler> routers) {
        Map<String, HttpHandler> orderedRouters = sortHttpHandlers(routers);

        RouterFunctions.Builder configuration = RouterFunctions.route();

        for (var router : routers.entrySet()) {
            configuration = configuration.add(router.getValue().getRoutingFunction());
            LOG.info("Router with name {} has been successfully registered.", router.getKey());
        }

        return configuration.build();
    }

    public static RequestContextFilter createRequestContextFilter(int priority) {
        return new RequestContextFilter(priority);
    }

    public static RequestContextFilter createRequestContextFilter() {
        return new RequestContextFilter(LOWEST_PRECEDENCE);
    }

    public static RequestContextFilter createRequestContextFilter(List<String> applicationHeadersNames) {
        return new RequestContextFilter(LOWEST_PRECEDENCE, applicationHeadersNames);
    }

    private static Map<String, HttpHandler> sortHttpHandlers(Map<String, HttpHandler> routers) {
        return routers.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1, LinkedHashMap::new));
    }
}
