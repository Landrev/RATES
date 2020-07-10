package com.github.landrev.rates.http.server.request;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class SubscriberHttpRequestContext implements HttpRequestContext {

    @Override
    public Mono<RequestData> getRequestData() {
        return Mono.subscriberContext().map($context -> $context.get(RequestData.class));
    }

    @Override
    public Mono<String> getRequestId() {
        return getRequestData().map(RequestData::getId);
    }

    @Override
    public Mono<String> getRequestPath() {
        return getRequestData().map(RequestData::getPath);
    }

    @Override
    public Mono<String> getRequestHost() {
        return getRequestData().map(RequestData::getHost);
    }

    @Override
    public Mono<Integer> getRequestPort() {
        return getRequestData().map(RequestData::getPort);
    }

    @Override
    public Mono<String> getPreferredLanguage() {
        return getRequestData().map(RequestData::getAcceptedLanguages)
                .map($acceptedLanguages -> $acceptedLanguages.stream().findFirst())
                .flatMap(Mono::justOrEmpty);
    }

    @Override
    public Flux<String> getAcceptedLanguages() {
        return getRequestData().map(RequestData::getAcceptedLanguages).flatMapMany(Flux::fromIterable);
    }
}
