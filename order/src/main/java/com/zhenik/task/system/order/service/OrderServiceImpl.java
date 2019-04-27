package com.zhenik.task.system.order.service;

import com.zhenik.task.system.order.domain.converter.OrderRepresentationConverter;
import com.zhenik.task.system.order.domain.representation.OrderJsonRepresentation;
import com.zhenik.task.system.order.repository.OrderRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
  private OrderRepository repository;
  private OrderRepresentationConverter converter;

  public OrderServiceImpl(OrderRepository repository, OrderRepresentationConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }

  @Override
  public List<OrderJsonRepresentation> getOrders() {
    return converter.transform(repository.findAll());
  }
}
