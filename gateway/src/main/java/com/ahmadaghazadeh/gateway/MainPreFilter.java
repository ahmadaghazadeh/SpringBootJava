package com.ahmadaghazadeh.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.function.Consumer;

@Component
public class MainPreFilter implements GlobalFilter, Ordered {
    private final Logger logger = LoggerFactory.getLogger(MainPreFilter.class);

    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("My first Pre-filter is executed...");
        String requestPath = exchange.getRequest().getPath().toString();
        logger.info("Request path = " + requestPath);
        HttpHeaders headers = exchange.getRequest().getHeaders();
        Set<String> headerNames = headers.keySet();
        headerNames.forEach(new Consumer<String>() {
            @Override
            public void accept(String headerName) {
                String headerValue = headers.getFirst(headerName);
                logger.info(headerName + " " + headerValue);
            }
        });
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
