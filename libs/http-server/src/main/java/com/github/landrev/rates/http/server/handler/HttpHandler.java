package com.github.landrev.rates.http.server.handler;

import com.github.landrev.rates.types.priority.Priority;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

public interface HttpHandler extends Comparable<HttpHandler> {

    RouterFunction<ServerResponse> getRoutingFunction();

    Priority getPriority();
}
