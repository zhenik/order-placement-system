package com.zhenik.task.system.customer.controller;

import com.zhenik.task.system.customer.domain.representation.CustomerJsonRepresentation;
import com.zhenik.task.system.customer.service.CustomerService;
import java.util.List;
import javax.validation.ConstraintViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "/customers")
@RestController
@Validated
public class CustomerController {
  private final CustomerService customerService;

  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<List<CustomerJsonRepresentation>> getCustomers() {
    return ResponseEntity.ok(customerService.getCustomers());
  }

  @GetMapping(
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
      path = "/{id}"
  )
  public ResponseEntity<CustomerJsonRepresentation> getCustomer(@PathVariable("id") Long id) {
    try {
     CustomerJsonRepresentation customer = customerService.getCustomer(id);
     return ResponseEntity.ok().body(customer);
    } catch (IllegalArgumentException exception) {
      return ResponseEntity.status(400).build();
    }

  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<Long> registerNewCustomer(
      @RequestBody CustomerJsonRepresentation customerJsonRepresentation) {

    if (customerJsonRepresentation.getId() != null) {
      return ResponseEntity.status(400).build();
    }
    try {
      Long id = customerService.registerCustomer(customerJsonRepresentation);
      return ResponseEntity.status(201).body(id);
    } catch (ConstraintViolationException e){
      return ResponseEntity.status(400).build();
    }
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity deleteCustomer(@PathVariable("id") Long customerId) {
    if (customerService.deleteCustomer(customerId)) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.badRequest().build();
    }
  }
}
