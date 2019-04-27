package com.zhenik.task.system.order.controller;

import com.zhenik.task.system.order.domain.representation.OrderJsonRepresentation;
import com.zhenik.task.system.order.service.OrderService;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "/orders")
@RestController
@Validated
public class OrderController {
  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<List<OrderJsonRepresentation>> getOrders() {
    return ResponseEntity.ok(orderService.getOrders());
  }

}
