package com.zhenik.task.system.customer.repository;

import com.zhenik.task.system.customer.Util;
import com.zhenik.task.system.customer.domain.model.Customer;
import javax.validation.ConstraintViolationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerRepositoryImplTest {

  @Autowired private CustomerRepository repository;

  @Test
  public void create_customer_valid() {
    Customer customer = Util.getCustomer();
    Long id1 = repository.createCustomer(customer);
    assertNotNull(id1);
  }

  @Test
  public void create_customer_valid_twice() {
    // no constraints about uniqueness of user props
    Long id1 = repository.createCustomer(Util.getCustomer());
    Long id2 = repository.createCustomer(Util.getCustomer());
    assertNotNull(id1);
    assertNotNull(id2);
    assertNotEquals(id1, id2);
  }

  @Test
  public void create_customer_invalid_email() {
    String invalidEmail1 = "";
    String invalidEmail2 = "#";
    String invalidEmail3 = "dafa.asda!.com";
    String invalidEmail4 = ".com";

    try {
      repository.createCustomer(Util.getCustomerWithEmail(invalidEmail1));
      fail();
    } catch (ConstraintViolationException ignored) {
    }

    try {
      repository.createCustomer(Util.getCustomerWithEmail(invalidEmail2));
      fail();
    } catch (ConstraintViolationException ignored) {
    }

    try {
      repository.createCustomer(Util.getCustomerWithEmail(invalidEmail3));
      fail();
    } catch (ConstraintViolationException ignored) {
    }

    try {
      repository.createCustomer(Util.getCustomerWithEmail(invalidEmail4));
      fail();
    } catch (ConstraintViolationException ignored) {
    }
  }

  @Test
  public void create_customer_invalid_phone() {
    String invalidPhone1 = "";
    String invalidPhone2 = "#";
    String invalidPhone3 = "asdaba1231";
    String invalidPhone4 = "+-2321";

    try {
      repository.createCustomer(Util.getCustomerWithPhoneNumber(invalidPhone1));
      fail();
    } catch (ConstraintViolationException ignored) {
    }

    try {
      repository.createCustomer(Util.getCustomerWithPhoneNumber(invalidPhone2));
      fail();
    } catch (ConstraintViolationException ignored) {
    }

    try {
      repository.createCustomer(Util.getCustomerWithPhoneNumber(invalidPhone3));
      fail();
    } catch (ConstraintViolationException ignored) {
    }

    try {
      repository.createCustomer(Util.getCustomerWithPhoneNumber(invalidPhone4));
      fail();
    } catch (ConstraintViolationException ignored) {
    }
  }


}
