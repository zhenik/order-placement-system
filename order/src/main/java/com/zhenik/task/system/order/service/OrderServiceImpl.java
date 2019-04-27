package com.zhenik.task.system.order.service;

import com.zhenik.task.system.order.domain.converter.OrderRepresentationConverter;
import com.zhenik.task.system.order.domain.model.Order;
import com.zhenik.task.system.order.domain.representation.OrderJsonRepresentation;
import com.zhenik.task.system.order.repository.OrderRepository;
import java.time.Instant;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
  private OrderRepository repository;
  private OrderRepresentationConverter converter;
  private CustomerClientImpl customerClient;

  public OrderServiceImpl(OrderRepository repository,
      OrderRepresentationConverter converter,
      CustomerClientImpl customerClient) {
    this.repository = repository;
    this.converter = converter;
    this.customerClient = customerClient;
  }

  @Override
  public List<OrderJsonRepresentation> getOrders() {
    return converter.transform(repository.findAll());
  }

  @Override public Long placeOrder(OrderJsonRepresentation orderJsonRepresentation) {
    boolean customerExist = customerClient.isCustomerExist(orderJsonRepresentation.getCustomerId());
    if (customerExist) {
      long timestamp = Instant.now().toEpochMilli();
      Order order = converter.transform(orderJsonRepresentation);
      order.setCreationDate(timestamp);
      order.setUpdateDate(timestamp);
      return repository.createOrder(order);
    } else {
      throw new IllegalArgumentException("Customer with id "+orderJsonRepresentation.getCustomerId()+" does not exist");
    }
  }
}
