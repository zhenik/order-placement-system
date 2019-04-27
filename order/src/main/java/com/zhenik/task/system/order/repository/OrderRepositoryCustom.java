package com.zhenik.task.system.order.repository;

import com.zhenik.task.system.order.domain.model.Order;

public interface OrderRepositoryCustom {
  Long createOrder(Order customer);
}
