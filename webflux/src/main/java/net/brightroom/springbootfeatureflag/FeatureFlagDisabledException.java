package net.brightroom.springbootfeatureflag;

public class FeatureFlagDisabledException extends RuntimeException {
  public FeatureFlagDisabledException(String message) {
    super(message);
  }
}
