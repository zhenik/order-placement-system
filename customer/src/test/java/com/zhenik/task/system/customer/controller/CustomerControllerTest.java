package com.zhenik.task.system.customer.controller;

import com.zhenik.task.system.customer.Util;
import com.zhenik.task.system.customer.domain.representation.CustomerJsonRepresentation;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class CustomerControllerTest extends CCTestBase {

  @Test
  public void clean_db() {
    RestAssured.given().get().then().statusCode(200).body("size()", CoreMatchers.equalTo(0));
  }

  @Test
  public void register_new_customer_valid() {
    CustomerJsonRepresentation customerJsonPresentation = Util.getCustomerJsonPresentation();
    Long id = RestAssured.given()
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
