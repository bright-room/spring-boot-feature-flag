package net.brightroom.springbootfeatureflag;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties(FeatureFlagProperties.class)
class FeatureFlagAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean(FeatureFlagProvider.class)
  FeatureFlagProvider featureFlagProvider(FeatureFlagProperties properties) {
    return new InMemoryFeatureFlagProvider(properties.features());
  }
}
