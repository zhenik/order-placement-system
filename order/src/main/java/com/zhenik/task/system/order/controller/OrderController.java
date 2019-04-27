package com.zhenik.task.system.order.controller;

import com.zhenik.task.system.order.domain.converter.OrderRepresentationConverter;
import com.zhenik.task.system.order.domain.representation.OrderJsonRepresentation;
import com.zhenik.task.system.order.domain.model.Order;
import com.zhenik.task.system.order.repository.OrderRepository;
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
  private final OrderRepository repository;
  private OrderRepresentationConverter converter;

  public OrderController(OrderRepository repository, OrderRepresentationConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<List<OrderJsonRepresentation>> getItems() {
    final List<Order> orders = repository.findAll();
    final List<OrderJsonRepresentation> jsonItems = converter.transform(orders);
    return ResponseEntity.ok(jsonItems);
  }

}
