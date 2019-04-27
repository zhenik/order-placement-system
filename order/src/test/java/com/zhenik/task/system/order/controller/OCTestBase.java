package com.zhenik.task.system.order.controller;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.zhenik.task.system.order.OrderApplication;
import com.zhenik.task.system.order.domain.representation.OrderJsonRepresentation;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(
    classes = OrderApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class OCTestBase {
  private Logger LOG = LoggerFactory.getLogger(OCTestBase.class.getCanonicalName());

  @LocalServerPort protected int port = 0;
  public static WireMockServer customerServer;

  @Before
  @After
  public void clean() {
    LOG.info("TEST: application port {}", port);
    LOG.info("TEST: customerServer port {}", customerServer.port());

    // RestAssured configs shared by all the tests
    RestAssured.baseURI = "http://localhost";
    RestAssured.port = port;
    RestAssured.basePath = "/orders";
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

    /** read each resource (GET), and then delete them one by one (DELETE) */
    final List<OrderJsonRepresentation> customerJsonRepresentations =
        Arrays.asList(
            RestAssured.given()
                .accept(ContentType.JSON)
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(OrderJsonRepresentation[].class));

    customerJsonRepresentations.forEach(
        customer ->
            RestAssured.given()
                .pathParam("id", customer.getId())
                .delete("/{id}")
                .then()
                .statusCode(204));

    RestAssured.given().get().then().statusCode(200).body("size()", CoreMatchers.equalTo(0));
  }

  @BeforeClass
  public static void setupBeforeAllTests() {
    customerServer =
        new WireMockServer(
            WireMockConfiguration.wireMockConfig()
                // NB! hardcoded
                .port(11111)
                // .dynamicPort()
                .notifier(new ConsoleNotifier(true)));
    customerServer.start();
  }

  @AfterClass
  public static void tearDownAfterAllTests() {
    Optional.ofNullable(customerServer).ifPresent(WireMockServer::stop);
  }
}
