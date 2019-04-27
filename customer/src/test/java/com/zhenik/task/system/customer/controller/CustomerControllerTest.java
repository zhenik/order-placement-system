package com.zhenik.task.system.customer.controller;

import com.zhenik.task.system.customer.Util;
import com.zhenik.task.system.customer.domain.representation.CustomerJsonRepresentation;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CustomerControllerTest extends CCTestBase {

  @Test
  public void clean_db() {
    RestAssured.given().get().then().statusCode(200).body("size()", CoreMatchers.equalTo(0));
  }

  @Test
  public void register_new_customer_valid() {
    CustomerJsonRepresentation customerJsonPresentation = Util.getCustomerJsonPresentation();
    Long id =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(customerJsonPresentation)
            .post()
            .then()
            .statusCode(201)
            .extract()
            .as(Long.class);

    assertNotNull(id);
  }

  @Test
  public void get_customer_exist() {
    CustomerJsonRepresentation customerJsonPresentation = Util.getCustomerJsonPresentation();
    Long id =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(customerJsonPresentation)
            .post()
            .then()
            .statusCode(201)
            .extract()
            .as(Long.class);

    CustomerJsonRepresentation fetchedCustomer =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .pathParam("id", id)
            .get("/{id}")
            .then()
            .statusCode(200)
            .extract()
            .as(CustomerJsonRepresentation.class);

    assertNotNull(fetchedCustomer);
    assertEquals(id, fetchedCustomer.getId());
  }

  @Test
  public void get_customer_not_exist_or_invalid_id() {
    Long id = Long.MAX_VALUE;
    RestAssured.given()
        .contentType(ContentType.JSON)
        .pathParam("id", id)
        .get("/{id}")
        .then()
        .statusCode(400);

    RestAssured.given()
        .contentType(ContentType.JSON)
        .pathParam("id", "asda")
        .get("/{id}")
        .then()
        .statusCode(400);
  }

  @Test
  public void delete_customer_success() {
    CustomerJsonRepresentation customerJsonPresentation = Util.getCustomerJsonPresentation();
    Long id =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(customerJsonPresentation)
            .post()
            .then()
            .statusCode(201)
            .extract()
            .as(Long.class);

    RestAssured.given().pathParam("id", id).delete("/{id}").then().statusCode(204);

    RestAssured.given()
        .contentType(ContentType.JSON)
        .pathParam("id", id)
        .get("/{id}")
        .then()
        .statusCode(400);
  }

  @Test
  public void register_new_customer_invalid() {
    CustomerJsonRepresentation customerJsonPresentation = Util.getCustomerJsonPresentation();

    // id is present
    customerJsonPresentation.setId(132123L);
    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(customerJsonPresentation)
        .post()
        .then()
        .statusCode(400);

    // empty email
    customerJsonPresentation = Util.getCustomerJsonPresentation();
    customerJsonPresentation.setEmail("");
    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(customerJsonPresentation)
        .post()
        .then()
        .statusCode(400);

    // invalid phone number
    customerJsonPresentation = Util.getCustomerJsonPresentation();
    customerJsonPresentation.setPhoneNumber("#2143fsdf");
    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(customerJsonPresentation)
        .post()
        .then()
        .statusCode(400);
  }
}
