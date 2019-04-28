package com.zhenik.task.system.e2e;

import com.zhenik.task.system.e2e.representations.CustomerJsonRepresentation;
import com.zhenik.task.system.e2e.representations.OrderJsonRepresentation;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.io.File;
import org.junit.ClassRule;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

public abstract class BaseIT {
  public static final String HOST = "http://localhost";
  public static final String CUSTOMERS_URI = HOST + ":" + "9082/customers";
  public static final String ORDERS_URI = HOST + ":" + "9081/orders";

  @ClassRule
  public static DockerComposeContainer environment =
      new DockerComposeContainer(new File("../docker-compose.yml"))
          .withLocalCompose(true)
          .waitingFor("customer-server_1", Wait.forHttp("/customers").forStatusCode(200))
          .waitingFor("order-server_1", Wait.forHttp("/orders").forStatusCode(200));

  public Long registerCustomer(CustomerJsonRepresentation customerJsonRepresentation) {
    return RestAssured.given()
        .contentType(ContentType.JSON)
        .body(customerJsonRepresentation)
        .post(CUSTOMERS_URI)
        .then()
        .statusCode(201)
        .extract()
        .as(Long.class);
  }

  public CustomerJsonRepresentation getCustomer(Long id) {
    return RestAssured.given()
        .contentType(ContentType.JSON)
        .pathParam("id", id)
        .get(CUSTOMERS_URI + "/{id}")
        .then()
        .statusCode(200)
        .extract()
        .as(CustomerJsonRepresentation.class);
  }

  public Long placeNewOrder(OrderJsonRepresentation orderJsonRepresentation) {
    return RestAssured.given()
        .contentType(ContentType.JSON)
        .body(orderJsonRepresentation)
        .post(ORDERS_URI)
        .then()
        .statusCode(201)
        .extract()
        .as(Long.class);
  }

  public OrderJsonRepresentation getOrder(Long id) {
    return RestAssured.given()
        .contentType(ContentType.JSON)
        .pathParam("id", id)
        .get(ORDERS_URI + "/{id}")
        .then()
        .statusCode(200)
        .extract()
        .as(OrderJsonRepresentation.class);
  }

  public void updateOrder(Long orderId, OrderJsonRepresentation updatedOrderRepresentation) {
    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(updatedOrderRepresentation)
        .pathParam("id", orderId)
        .put(ORDERS_URI + "/{id}")
        .then()
        .statusCode(200);
  }
}
