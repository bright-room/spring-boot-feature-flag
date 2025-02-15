package net.brightroom.springbootfeatureflag;

import java.util.Objects;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Order(Ordered.HIGHEST_PRECEDENCE)
class FeatureFlagWebFilter implements WebFilter {

  FeatureFlagProvider featureFlagProvider;
  RequestMappingHandlerMapping handlerMapping;

  @Override
  @NonNull
  public Mono<Void> filter(@NonNull ServerWebExchange exchange, WebFilterChain chain) {
    return handlerMapping
        .getHandler(exchange)
        .cast(HandlerMethod.class)
        .flatMap(handlerMethod -> checkFeatureFlags(handlerMethod, exchange))
        .then(chain.filter(exchange))
        .onErrorResume(
            FeatureFlagDisabledException.class, ex -> exchange.getResponse().setComplete());
  }

  private Mono<Void> checkFeatureFlags(HandlerMethod handlerMethod, ServerWebExchange exchange) {
    FeatureFlag methodAnnotation = handlerMethod.getMethodAnnotation(FeatureFlag.class);
    if (Objects.nonNull(methodAnnotation) && isFeatureEnabled(methodAnnotation)) {
      return handleDisabledFeature(exchange);
    }

    FeatureFlag classAnnotation = handlerMethod.getBeanType().getAnnotation(FeatureFlag.class);
    if (Objects.nonNull(classAnnotation) && isFeatureEnabled(classAnnotation)) {
      return handleDisabledFeature(exchange);
    }

    return Mono.empty();
  }

  private boolean isFeatureEnabled(FeatureFlag annotation) {
    if (!annotation.required()) return false;
    return !featureFlagProvider.isFeatureEnabled(annotation.feature());
  }

  private Mono<Void> handleDisabledFeature(ServerWebExchange exchange) {
    ServerHttpResponse response = exchange.getResponse();
    response.setStatusCode(HttpStatus.METHOD_NOT_ALLOWED);

    DataBufferFactory bufferFactory = response.bufferFactory();
    DataBuffer buffer = bufferFactory.wrap("This feature is not available".getBytes());

    Mono<DataBuffer> responseBody = Mono.just(buffer);

    return response.writeWith(responseBody);
  }

  FeatureFlagWebFilter(
      FeatureFlagProvider featureFlagProvider, RequestMappingHandlerMapping handlerMapping) {
    this.featureFlagProvider = featureFlagProvider;
    this.handlerMapping = handlerMapping;
  }
}
