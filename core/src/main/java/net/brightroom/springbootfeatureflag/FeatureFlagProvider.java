package net.brightroom.springbootfeatureflag;

interface FeatureFlagProvider {
  boolean isFeatureEnabled(String featureName);
}
