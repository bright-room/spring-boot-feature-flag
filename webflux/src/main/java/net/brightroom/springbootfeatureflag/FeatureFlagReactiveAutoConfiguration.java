package net.brightroom.springbootfeatureflag;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;

@AutoConfigureAfter(FeatureFlagAutoConfiguration.class)
class FeatureFlagReactiveAutoConfiguration {

  @Bean
  FeatureFlagWebFilter featureFlagInterceptor(
      FeatureFlagProvider featureFlagProvider, RequestMappingHandlerMapping handlerMapping) {
    return new FeatureFlagWebFilter(featureFlagProvider, handlerMapping);
  }
}
