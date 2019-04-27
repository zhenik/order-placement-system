package com.zhenik.task.system.customer.domain.converter;

import com.zhenik.task.system.customer.domain.model.Customer;
import com.zhenik.task.system.customer.domain.representation.CustomerJsonRepresentation;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerRepresentationConverterTest {

  private static CustomerRepresentationConverter converter = new CustomerRepresentationConverter();

  @Test
  public void transform_customer_to_json_presentation() {
    final Customer customer = getCustomer();
    final CustomerJsonRepresentation customerJsonRepresentation = converter.transform(customer);

    assertEquals(customer.getId(), customerJsonRepresentation.getId());
    assertEquals(customer.getName(), customerJsonRepresentation.getName());
    assertEquals(customer.getEmail(), customerJsonRepresentation.getEmail());
    assertEquals(customer.getPhoneNumber(), customerJsonRepresentation.getPhoneNumber());
  }

  public Customer getCustomer() {
    return new Customer(1L, "John Doe", "+4712373123", "john.doe@example.com");
  }
}
