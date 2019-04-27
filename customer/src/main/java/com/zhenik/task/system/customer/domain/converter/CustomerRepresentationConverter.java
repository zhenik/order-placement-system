package com.zhenik.task.system.customer.domain.converter;

import com.zhenik.task.system.customer.domain.model.Customer;
import com.zhenik.task.system.customer.domain.representation.CustomerJsonRepresentation;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class CustomerRepresentationConverter {
  public CustomerJsonRepresentation transform(Customer customer) {
    return new CustomerJsonRepresentation(
        customer.getId(), customer.getName(), customer.getPhoneNumber(), customer.getEmail());
  }

  public List<CustomerJsonRepresentation> transform(List<Customer> customers) {
    return customers.stream().map(this::transform).collect(Collectors.toList());
  }

  public Customer transform(CustomerJsonRepresentation customerJsonRepresentation) {
    return new Customer(
        customerJsonRepresentation.getId(),
        customerJsonRepresentation.getName(),
        customerJsonRepresentation.getPhoneNumber(),
        customerJsonRepresentation.getEmail());
  }
}
