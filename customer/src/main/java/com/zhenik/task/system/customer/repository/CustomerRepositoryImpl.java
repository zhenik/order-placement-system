package com.zhenik.task.system.customer.repository;

import com.zhenik.task.system.customer.domain.model.Customer;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class CustomerRepositoryImpl implements CustomerRepositoryCustom {

  @PersistenceContext
  private EntityManager em;

  @Override public Long createCustomer(Customer customer) {
    em.persist(customer);
    return customer.getId();
  }
}
