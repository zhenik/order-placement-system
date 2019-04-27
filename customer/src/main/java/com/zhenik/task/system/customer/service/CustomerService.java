package com.zhenik.task.system.customer.service;

import com.zhenik.task.system.customer.domain.representation.CustomerJsonRepresentation;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService {
  List<CustomerJsonRepresentation> getCustomers();
  CustomerJsonRepresentation getCustomer(Long id);
  Long registerCustomer(CustomerJsonRepresentation customerJsonRepresentation);
  Boolean deleteCustomer(Long id);
}
