package com.github.landrev.rates.http.server.request;

import com.github.landrev.rates.types.pair.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.reactive.filter.OrderedWebFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RequestContextFilter implements OrderedWebFilter {

    private static final Logger LOG = LoggerFactory.getLogger(RequestContextFilter.class);

    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";

    private final int priority;
    private final List<String> applicationHeadersNames;

    public RequestContextFilter(final int priority) {
        this.priority = priority;
        this.applicationHeadersNames = Collections.emptyList();
    }

    public RequestContextFilter(final int priority, List<String> applicationHeadersNames) {
        this.priority = priority;
        this.applicationHeadersNames = applicationHeadersNames;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        ServerHttpRequest request = serverWebExchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        InetSocketAddress address = request.getRemoteAddress();
        String hostString = address != null ? address.getHostString() : "UNDEFINED";

        Stream<String> defaultAcceptedLanguages = Stream.of("en");
        Stream<String> acceptedLanguagesFromHeaders = headers
                .getAcceptLanguageAsLocales()
                .stream()
                .map(Locale::getLanguage);

        List<String> acceptedLanguages = Stream.concat(acceptedLanguagesFromHeaders, defaultAcceptedLanguages)
                .distinct()
                .collect(Collectors.toList());

        String authorizationHeader = headers.getFirst(AUTHORIZATION_HEADER_NAME);

        Map<String, List<String>> applicationHeaders = applicationHeadersNames.stream()
                .map($applicationHeader -> Pair.create($applicationHeader, headers.getOrEmpty($applicationHeader)))
                .filter($header -> !$header.getSecond().isEmpty())
                .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));

        String requestId = generateUniqueRequestId(request.getId());
        RequestData requestData = RequestData.builder()
                .id(requestId)
                .path(request.getPath().value())
                .host(address != null ? address.getAddress().getHostAddress() : null)
                .port(address != null ? address.getPort() : null)
                .acceptedLanguages(acceptedLanguages)
                .applicationHeaders(applicationHeaders)
                .build();

        LOG.info("An API {} {} has been called from {} as {}.",
                request.getMethodValue(),
                request.getPath(),
                hostString,
                requestId);

        return webFilterChain.filter(serverWebExchange)
                .subscriberContext($context -> $context.put(RequestData.class, requestData))
                .doOnSuccess($ -> LOG.info("Request {} has been successfully completed.", requestId))
                .doOnError($ -> LOG.info("Request {} has been unsuccessfully completed.", requestId))
                .then();
    }

    @Override
    public int getOrder() {
        return priority;
    }

    private String generateUniqueRequestId(String tcpSessionId) {
        String separator = "_";
        return tcpSessionId + separator + Thread.currentThread().getId() + separator + UUID.randomUUID();
    }
}

