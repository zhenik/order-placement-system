package com.zhenik.task.system.order.controller;

import com.zhenik.task.system.order.domain.representation.OrderJsonRepresentation;
import com.zhenik.task.system.order.service.OrderService;
import java.util.List;
import javax.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "/orders")
@RestController
@Validated
public class OrderController {
  private static Logger LOG = LoggerFactory.getLogger(OrderController.class);
  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<List<OrderJsonRepresentation>> getOrders() {
    return ResponseEntity.ok(orderService.getOrders());
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<Long> placeOrder(@RequestBody OrderJsonRepresentation orderJsonRepresentation) {
    if (orderJsonRepresentation.getId() != null) {
      LOG.warn("Order id is present: {}", orderJsonRepresentation.getId());
      return ResponseEntity.status(400).build();
    }
    try {
      Long id = orderService.placeOrder(orderJsonRepresentation);
      return ResponseEntity.status(201).body(id);
    } catch (ConstraintViolationException | IllegalArgumentException e) {
      LOG.error("Fail: {}",e.toString());
      return ResponseEntity.status(400).build();
    }
  }

}
