package com.zhenik.task.system.order.domain.converter;

import com.zhenik.task.system.order.domain.model.Order;
import com.zhenik.task.system.order.domain.representation.OrderJsonRepresentation;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class OrderRepresentationConverter {
  public OrderJsonRepresentation transform(Order order) {
    return new OrderJsonRepresentation(order.getId(), order.getName(), order.getDescription());
  }

  public List<OrderJsonRepresentation> transform(List<Order> orderList) {
    return orderList.stream().map(this::transform).collect(Collectors.toList());
  }

  public Order transform(OrderJsonRepresentation json) {
    return new Order(json.getId(), json.getName(), json.getDescription());
  }
}
