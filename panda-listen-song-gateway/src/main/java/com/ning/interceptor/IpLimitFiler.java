package com.ning.interceptor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
public class IpLimitFiler implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String ip = request.getHeaders().getHost().getHostName();
        String uri = request.getURI().getPath();
        System.out.println(ip+"..."+uri);
        return chain.filter(exchange);

    }

    @Override
    public int getOrder() {
        return -1;
    }
}
