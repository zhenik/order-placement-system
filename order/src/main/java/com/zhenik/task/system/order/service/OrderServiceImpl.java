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

  @Override public OrderJsonRepresentation getOrder(Long id) {
    Order order = repository.getOne(id);
    if (order!=null){
      return converter.transform(order);
    } else {
      throw new IllegalArgumentException("Order with id" + id + " does not exist" );
    }
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

  @Override public Boolean updateOrder(Long id, OrderJsonRepresentation orderJsonRepresentation) {
    // validation: if customer updated
    boolean customerExist = customerClient.isCustomerExist(orderJsonRepresentation.getCustomerId());

    if (customerExist) {
      long timestamp = Instant.now().toEpochMilli();
      orderJsonRepresentation.setUpdateDate(timestamp);
      Order order = converter.transform(orderJsonRepresentation);
      return repository.updateOrder(id, order);
    } else {
      throw new IllegalArgumentException("Customer with id "+orderJsonRepresentation.getCustomerId()+" does not exist");
    }
  }

  @Override public Boolean deleteOrder(Long id) {
    if (repository.exists(id)) {
      repository.delete(id);
      return true;
    } else {
      return false;
    }
  }
}
