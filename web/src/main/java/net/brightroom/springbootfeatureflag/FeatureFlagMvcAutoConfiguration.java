package net.brightroom.springbootfeatureflag;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;

/**
 * Autoconfiguration for Feature Flag MVC support. Configures the Feature Flag interceptor for
 * Spring MVC applications. This configuration is loaded after the main Feature Flag
 * autoconfiguration.
 */
@AutoConfigureAfter(FeatureFlagAutoConfiguration.class)
public class FeatureFlagMvcAutoConfiguration {

  @Bean
  FeatureFlagInterceptor featureFlagInterceptor(FeatureFlagProvider featureFlagProvider) {
    return new FeatureFlagInterceptor(featureFlagProvider);
  }

  FeatureFlagMvcAutoConfiguration() {}
}
