package com.zhenik.task.system.order.controller;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.zhenik.task.system.order.Util;
import com.zhenik.task.system.order.domain.representation.CustomerJsonRepresentation;
import com.zhenik.task.system.order.domain.representation.OrderJsonRepresentation;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class OrderControllerTest extends OCTestBase {

  @Test
  public void clean_db() {
    RestAssured.given().get().then().statusCode(200).body("size()", CoreMatchers.equalTo(0));
  }

  @Test
  public void place_order_valid() {
    OrderJsonRepresentation orderJsonRepresentation = Util.getOrder();
    CustomerJsonRepresentation jsonBody = Util.getCustomerJsonRepresentation();

    customerServer.stubFor(
        get(urlMatching(".*/customers/" + orderJsonRepresentation.getCustomerId()))
            .willReturn(ResponseDefinitionBuilder.okForJson(jsonBody)));

    Long orderId =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(orderJsonRepresentation)
            .post()
            .then()
            .statusCode(201)
            .extract()
            .as(Long.class);

    assertNotNull(orderId);
  }

  @Test
  public void place_order_invalid() {
    OrderJsonRepresentation orderJsonRepresentation = Util.getOrder();
    orderJsonRepresentation.setId(5L);

    RestAssured.given()
        .accept(ContentType.JSON)
        .contentType(ContentType.JSON)
        .body(orderJsonRepresentation)
        .post()
        .then()
        .statusCode(400);
  }

  @Test
  public void place_order_customer_not_exists() {
    OrderJsonRepresentation orderJsonRepresentation = Util.getOrder();

    customerServer.stubFor(
        get(urlMatching(".*/customers/" + orderJsonRepresentation.getCustomerId()))
            .willReturn(aResponse().withStatus(400)));

    RestAssured.given()
        .accept(ContentType.JSON)
        .contentType(ContentType.JSON)
        .body(orderJsonRepresentation)
        .post()
        .then()
        .statusCode(404);
  }

  @Test
  public void get_orders() {
    // place
    OrderJsonRepresentation orderJsonRepresentation = Util.getOrder();
    CustomerJsonRepresentation jsonBody = Util.getCustomerJsonRepresentation();
    customerServer.stubFor(
        get(urlMatching(".*/customers/" + orderJsonRepresentation.getCustomerId()))
            .willReturn(ResponseDefinitionBuilder.okForJson(jsonBody)));
    Long orderId =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(orderJsonRepresentation)
            .post()
            .then()
            .statusCode(201)
            .extract()
            .as(Long.class);
    assertNotNull(orderId);

    List<OrderJsonRepresentation> orderList =
        Arrays.asList(
            RestAssured.given()
                .accept(ContentType.JSON)
                .get()
                .then()
                .statusCode(200)
                .extract()
                .as(OrderJsonRepresentation[].class));
    assertTrue(orderList.size() > 0);
  }

  @Test
  public void get_order_exist() {
    // place
    OrderJsonRepresentation orderJsonRepresentation = Util.getOrder();
    CustomerJsonRepresentation jsonBody = Util.getCustomerJsonRepresentation();
    customerServer.stubFor(
        get(urlMatching(".*/customers/" + orderJsonRepresentation.getCustomerId()))
            .willReturn(ResponseDefinitionBuilder.okForJson(jsonBody)));

    Long orderId =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(orderJsonRepresentation)
            .post()
            .then()
            .statusCode(201)
            .extract()
            .as(Long.class);
    assertNotNull(orderId);

    OrderJsonRepresentation orderPersisted =
        RestAssured.given()
            .accept(ContentType.JSON)
            .pathParam("id", orderId)
            .get("/{id}")
            .then()
            .statusCode(200)
            .extract()
            .as(OrderJsonRepresentation.class);

    assertNotNull(orderPersisted.getId());
  }

  @Test
  public void get_order_not_exist() {
    RestAssured.given()
        .accept(ContentType.JSON)
        .pathParam("id", Long.MAX_VALUE)
        .get("/{id}")
        .then()
        .statusCode(404);
  }

  @Test
  public void update_order_valid() {
    // place order
    OrderJsonRepresentation orderJsonRepresentation = Util.getOrder();
    CustomerJsonRepresentation jsonBody = Util.getCustomerJsonRepresentation();
    customerServer.stubFor(
        get(urlMatching(".*/customers/" + orderJsonRepresentation.getCustomerId()))
            .willReturn(ResponseDefinitionBuilder.okForJson(jsonBody)));
    Long orderId =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(orderJsonRepresentation)
            .post()
            .then()
            .statusCode(201)
            .extract()
            .as(Long.class);
    assertNotNull(orderId);

    // update
    OrderJsonRepresentation updatedOrderRepresentation = Util.getOrder();
    String newNote = UUID.randomUUID().toString();
    updatedOrderRepresentation.setNote(newNote);
    // update call
    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(updatedOrderRepresentation)
        .pathParam("id", orderId)
        .put("/{id}")
        .then()
        .statusCode(200);
  }

  @Test
  public void update_order_invalid_customer_id() {
    // place order
    OrderJsonRepresentation orderJsonRepresentation = Util.getOrder();
    CustomerJsonRepresentation jsonBody = Util.getCustomerJsonRepresentation();
    customerServer.stubFor(
        get(urlMatching(".*/customers/" + orderJsonRepresentation.getCustomerId()))
            .willReturn(ResponseDefinitionBuilder.okForJson(jsonBody)));
    Long orderId =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(orderJsonRepresentation)
            .post()
            .then()
            .statusCode(201)
            .extract()
            .as(Long.class);
    assertNotNull(orderId);

    // update
    OrderJsonRepresentation updatedOrderRepresentation = Util.getOrder();
    Long invalidCustomerId = Long.MAX_VALUE;
    updatedOrderRepresentation.setCustomerId(invalidCustomerId);

    customerServer.stubFor(
        get(urlMatching(".*/customers/" + invalidCustomerId))
            .willReturn(aResponse().withStatus(400)));

    // update call
    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(updatedOrderRepresentation)
        .pathParam("id", orderId)
        .put("/{id}")
        .then()
        .statusCode(400);
  }


}
