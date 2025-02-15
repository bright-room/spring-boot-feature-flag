package net.brightroom.springbootfeatureflag;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "feature-flags")
class FeatureFlagProperties {

  Map<String, Boolean> features = new ConcurrentHashMap<>();

  Map<String, Boolean> features() {
    return features;
  }

  // for property injection
  void setFeatures(Map<String, Boolean> features) {
    this.features = features;
  }
}
