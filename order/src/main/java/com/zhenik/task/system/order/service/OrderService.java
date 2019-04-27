package com.zhenik.task.system.order.service;

import com.zhenik.task.system.order.domain.representation.OrderJsonRepresentation;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
  List<OrderJsonRepresentation> getOrders();
  OrderJsonRepresentation getOrder(Long id);
  Long placeOrder(OrderJsonRepresentation orderJsonRepresentation);
  Boolean updateOrder(Long id, OrderJsonRepresentation orderJsonRepresentation);
  Boolean deleteOrder(Long id);
}
