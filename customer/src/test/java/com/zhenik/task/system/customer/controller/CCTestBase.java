package com.zhenik.task.system.customer.controller;

import com.zhenik.task.system.customer.CustomerApplication;
import com.zhenik.task.system.customer.domain.representation.CustomerJsonRepresentation;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(
    classes = CustomerApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class CCTestBase {
  private Logger LOG = Logger.getLogger(CCTestBase.class.getCanonicalName());

  @LocalServerPort protected int port = 8081;

  @Before
  @After
  public void clean() {

    LOG.log(Level.INFO, String.valueOf(port));

    // RestAssured configs shared by all the tests
    RestAssured.baseURI = "http://localhost";
    RestAssured.port = port;
    RestAssured.basePath = "/customers";
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

    /** read each resource (GET), and then delete them one by one (DELETE) */
    final List<CustomerJsonRepresentation> customerJsonRepresentations =
        Arrays.asList(
            RestAssured.given()
                .accept(ContentType.JSON)
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(CustomerJsonRepresentation[].class));

    customerJsonRepresentations.forEach(
        customer -> RestAssured.given()
            .pathParam("id", customer.getId())
            .delete("/{id}")
            .then()
            .statusCode(204));

    RestAssured.given().get().then().statusCode(200).body("size()", CoreMatchers.equalTo(0));
  }
}
