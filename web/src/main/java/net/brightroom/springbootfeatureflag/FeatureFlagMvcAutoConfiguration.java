package net.brightroom.springbootfeatureflag;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;

@AutoConfigureAfter(FeatureFlagAutoConfiguration.class)
class FeatureFlagMvcAutoConfiguration {

  @Bean
  FeatureFlagInterceptor featureFlagInterceptor(FeatureFlagProvider featureFlagProvider) {
    return new FeatureFlagInterceptor(featureFlagProvider);
  }
}
