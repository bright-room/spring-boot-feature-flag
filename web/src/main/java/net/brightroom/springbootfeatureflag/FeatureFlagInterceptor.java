package net.brightroom.springbootfeatureflag;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

class FeatureFlagInterceptor implements HandlerInterceptor {

  FeatureFlagProvider featureFlagProvider;

  @Override
  public boolean preHandle(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull Object handler) {
    if (!(handler instanceof HandlerMethod handlerMethod)) {
      return true;
    }

    FeatureFlag methodAnnotation = handlerMethod.getMethodAnnotation(FeatureFlag.class);
    if (Objects.nonNull(methodAnnotation) && checkFeatureFlag(methodAnnotation)) {
      writeResponse(response);
      return false;
    }

    FeatureFlag classAnnotation = handlerMethod.getBeanType().getAnnotation(FeatureFlag.class);
    if (Objects.nonNull(classAnnotation) && checkFeatureFlag(classAnnotation)) {
      writeResponse(response);
      return false;
    }
    return true;
  }

  private boolean checkFeatureFlag(FeatureFlag annotation) {
    if (!annotation.required()) return false;
    return !featureFlagProvider.isFeatureEnabled(annotation.feature());
  }

  private void writeResponse(HttpServletResponse response) {
    try {
      response.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());

      PrintWriter writer = response.getWriter();
      writer.write("This feature is not available");
    } catch (IOException e) {
      throw new RuntimeException("Fail to write response.", e);
    }
  }

  FeatureFlagInterceptor(FeatureFlagProvider featureFlagProvider) {
    this.featureFlagProvider = featureFlagProvider;
  }
}
