package com.zhenik.task.system.order.domain.converter;

import com.zhenik.task.system.order.domain.model.Order;
import com.zhenik.task.system.order.domain.representation.OrderJsonRepresentation;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class OrderRepresentationConverter {
  public OrderJsonRepresentation transform(Order order) {
    return new OrderJsonRepresentation(
        order.getId(),
        order.getCustomerId(),
        order.getAddressFrom(),
        order.getAddressTo(),
        order.getService(),
        order.getCreationDate(),
        order.getUpdateDate(),
        order.getNote()
    );
  }

  public List<OrderJsonRepresentation> transform(List<Order> orderList) {
    return orderList.stream().map(this::transform).collect(Collectors.toList());
  }

  public Order transform(OrderJsonRepresentation orderJsonRepresentation) {
    return new Order(
        orderJsonRepresentation.getId(),
        orderJsonRepresentation.getCustomerId(),
        orderJsonRepresentation.getAddressFrom(),
        orderJsonRepresentation.getAddressTo(),
        orderJsonRepresentation.getService(),
        orderJsonRepresentation.getCreationDate(),
        orderJsonRepresentation.getUpdateDate(),
        orderJsonRepresentation.getNote()
    );
  }
}
