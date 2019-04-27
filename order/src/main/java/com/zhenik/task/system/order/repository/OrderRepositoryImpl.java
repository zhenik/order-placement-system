package com.zhenik.task.system.order.repository;

import com.zhenik.task.system.order.domain.model.Order;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class OrderRepositoryImpl implements OrderRepositoryCustom {

  @PersistenceContext
  private EntityManager em;

  @Override public Long createOrder(Order order) {
    em.persist(order);
    return order.getId();
  }
}
