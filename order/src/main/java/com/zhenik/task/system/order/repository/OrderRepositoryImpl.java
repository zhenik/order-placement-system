package com.zhenik.task.system.order.repository;

import com.zhenik.task.system.order.domain.model.Order;
import java.util.Optional;
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
      orderPersisted.setCustomerId(Optional.ofNullable(order.getCustomerId()).orElse(orderPersisted.getCustomerId()));
      orderPersisted.setAddressFrom(Optional.ofNullable(order.getAddressFrom()).orElse(orderPersisted.getAddressFrom()));
      orderPersisted.setAddressTo(Optional.ofNullable(order.getAddressTo()).orElse(orderPersisted.getAddressTo()));
      orderPersisted.setUpdateDate(Optional.ofNullable(order.getUpdateDate()).orElse(orderPersisted.getUpdateDate()));
      orderPersisted.setServiceEventDate(Optional.ofNullable(order.getServiceEventDate()).orElse(orderPersisted.getServiceEventDate()));
      orderPersisted.setService(Optional.ofNullable(order.getService()).orElse(orderPersisted.getService()));
      orderPersisted.setNote(Optional.ofNullable(order.getNote()).orElse(orderPersisted.getNote()));
      return true;
    } else {
      return false;
    }
  }

}
