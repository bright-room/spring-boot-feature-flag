package net.brightroom.springbootfeatureflag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = FeatureFlagAutoConfiguration.class)
class FeatureFlagAutoConfigurationTest {

  FeatureFlagProvider featureFlagProvider;

  @Test
  void providerFunctionsProperly() {
    assertTrue(featureFlagProvider.isFeatureEnabled("test-api"));
    assertTrue(featureFlagProvider.isFeatureEnabled("new-api"));
    assertFalse(featureFlagProvider.isFeatureEnabled("beta-feature"));
  }

  @Autowired
  FeatureFlagAutoConfigurationTest(FeatureFlagProvider featureFlagProvider) {
    this.featureFlagProvider = featureFlagProvider;
  }
}
