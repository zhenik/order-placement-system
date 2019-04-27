package com.zhenik.task.system.order.service;

import com.zhenik.task.system.order.domain.representation.OrderJsonRepresentation;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
  List<OrderJsonRepresentation> getOrders();
}
