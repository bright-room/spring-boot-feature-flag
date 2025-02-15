package net.brightroom.springbootfeatureflag;

import java.util.Map;

class InMemoryFeatureFlagProvider implements FeatureFlagProvider {

  Map<String, Boolean> features;

  InMemoryFeatureFlagProvider(Map<String, Boolean> features) {
    this.features = features;
  }

  @Override
  public boolean isFeatureEnabled(String featureName) {
    return features.getOrDefault(featureName, false);
  }
}
