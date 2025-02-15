package net.brightroom.springbootfeatureflag;

public interface FeatureFlagProvider {
  boolean isFeatureEnabled(String featureName);
}
