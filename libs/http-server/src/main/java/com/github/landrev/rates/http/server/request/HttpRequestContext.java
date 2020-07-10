package com.github.landrev.rates.http.server.request;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface HttpRequestContext {

    Mono<RequestData> getRequestData();

    Mono<String> getRequestId();

    Mono<String> getRequestPath();

    Mono<String> getRequestHost();

    Mono<Integer> getRequestPort();

    Mono<String> getPreferredLanguage();

    Flux<String> getAcceptedLanguages();
}
