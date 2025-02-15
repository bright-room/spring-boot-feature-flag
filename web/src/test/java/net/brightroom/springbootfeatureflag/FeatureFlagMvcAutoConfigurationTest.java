package net.brightroom.springbootfeatureflag;

import static io.restassured.RestAssured.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(
    classes = {
      FeatureFlagMvcAutoConfiguration.class,
      FeatureFlagAutoConfiguration.class,
      TestConfiguration.class
    },
    webEnvironment = RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FeatureFlagMvcAutoConfigurationTest {

  @LocalServerPort Integer port;

  @BeforeAll
  void setUp() {
    RestAssured.baseURI = "http://localhost";
    RestAssured.port = port;
  }

  @Test
  void test1() {
    given().when().get("/exist-endpoint").then().assertThat().statusCode(200);
  }

  @Test
  void test2() {
    given().when().get("/new-endpoint").then().assertThat().statusCode(200);
  }

  @Test
  void test3() {
    given().when().get("/beta-endpoint").then().assertThat().statusCode(405);
  }
}
