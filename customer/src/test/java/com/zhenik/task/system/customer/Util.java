package com.zhenik.task.system.customer;

import com.zhenik.task.system.customer.domain.model.Customer;
import com.zhenik.task.system.customer.domain.representation.CustomerJsonRepresentation;

public class Util {
  private static final String DEFAULT_NAME = "John Doe";
  private static final String DEFAULT_PHONE = "93973845";
  private static final String DEFAULT_EMAIL = "example@example.com";

  public static Customer getCustomer() {
    return new Customer(DEFAULT_NAME, DEFAULT_PHONE, DEFAULT_EMAIL);
  }

  public static Customer getCustomerWithEmail(String email) {
    Customer customer = getCustomer();
    customer.setEmail(email);
    return customer;
  }

  public static Customer getCustomerWithPhoneNumber(String phoneNumber) {
    Customer customer = getCustomer();
    customer.setPhoneNumber(phoneNumber);
    return customer;
  }

  public static CustomerJsonRepresentation getCustomerJsonPresentation() {
    return new CustomerJsonRepresentation(DEFAULT_NAME, DEFAULT_PHONE, DEFAULT_EMAIL);
  }
}
