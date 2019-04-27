package com.zhenik.task.system.customer.service;

import com.zhenik.task.system.customer.Util;
import com.zhenik.task.system.customer.domain.converter.CustomerRepresentationConverter;
import com.zhenik.task.system.customer.domain.representation.CustomerJsonRepresentation;
import com.zhenik.task.system.customer.repository.CustomerRepository;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerServiceImplTest {

  @Autowired private CustomerRepository repository;
  private CustomerRepresentationConverter converter;
  private CustomerService customerService;

  @Before
  public void startUp() {
    converter = new CustomerRepresentationConverter();
    customerService = new CustomerServiceImpl(repository, converter);
  }

  @After
  public void tearDown() {
    customerService = null;
    converter = null;
  }

  @Test
  public void get_customers() {
    repository.createCustomer(Util.getCustomer());
    List<CustomerJsonRepresentation> customers = customerService.getCustomers();
    assertTrue(customers.size() > 0);
  }

  @Test
  public void register_customer_valid() {
    Long id = customerService.registerCustomer(Util.getCustomerJsonPresentation());
    List<CustomerJsonRepresentation> customers = customerService.getCustomers();
    assertNotNull(id);
    assertTrue(customers.size() > 0);
  }

}
