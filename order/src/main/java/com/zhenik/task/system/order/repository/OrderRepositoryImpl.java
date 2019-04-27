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

  @Override public boolean updateOrder(Long id, Order order) {
    Order orderPersisted = em.find(Order.class, id);
    if (orderPersisted!=null){
      // not order id
      // not creation date
      orderPersisted.setCustomerId(order.getCustomerId());
      orderPersisted.setAddressFrom(order.getAddressFrom());
      orderPersisted.setAddressTo(order.getAddressTo());
      orderPersisted.setUpdateDate(order.getUpdateDate());
      orderPersisted.setService(order.getService());
      orderPersisted.setNote(order.getNote());
      return true;
    } else {
      return false;
    }

  }
}
