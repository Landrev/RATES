package com.github.landrev.rates.http.server.request;

import lombok.Getter;
import lombok.Value;
import lombok.Builder;

import java.util.List;
import java.util.Map;

@Value
@Builder
@Getter
public class RequestData {

    String id;

    String path;

    String host;

    Integer port;

    List<String> acceptedLanguages;

    Map<String, List<String>> applicationHeaders;
}
