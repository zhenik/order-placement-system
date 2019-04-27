package com.zhenik.task.system.customer.service;

import com.zhenik.task.system.customer.domain.converter.CustomerRepresentationConverter;
import com.zhenik.task.system.customer.domain.representation.CustomerJsonRepresentation;
import com.zhenik.task.system.customer.repository.CustomerRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
  private CustomerRepository repository;
  private CustomerRepresentationConverter converter;

  public CustomerServiceImpl(
      CustomerRepository repository, CustomerRepresentationConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }

  @Override
  public List<CustomerJsonRepresentation> getCustomers() {
    return converter.transform(repository.findAll());
  }
}
