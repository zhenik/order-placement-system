package com.zhenik.task.system.customer.controller;

import com.zhenik.task.system.customer.domain.representation.CustomerJsonRepresentation;
import com.zhenik.task.system.customer.service.CustomerService;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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
}
