package com.zhenik.task.system.order.controller;

import com.zhenik.task.system.order.domain.representation.OrderJsonRepresentation;
import com.zhenik.task.system.order.service.OrderService;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<OrderJsonRepresentation>> getOrders() {
    return ResponseEntity.ok(orderService.getOrders());
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/{id}")
  public ResponseEntity<OrderJsonRepresentation> getOrder(@PathVariable("id") Long id) {
    try {
      OrderJsonRepresentation orderRepresentation = orderService.getOrder(id);
      return ResponseEntity.ok(orderRepresentation);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    } catch (EntityNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Long> placeOrder(
      @RequestBody OrderJsonRepresentation orderJsonRepresentation) {
    if (orderJsonRepresentation.getId() != null) {
      LOG.warn("Order id is present: {}", orderJsonRepresentation.getId());
      return ResponseEntity.badRequest().build();
    }
    try {
      Long id = orderService.placeOrder(orderJsonRepresentation);
      return ResponseEntity.status(201).body(id);
    } catch (ConstraintViolationException e) {
      LOG.error("Fail: {}", e.toString());
      return ResponseEntity.badRequest().build();
    } catch (IllegalArgumentException e) {
      LOG.error("Fail: {}", e.toString());
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/{id}")
  public ResponseEntity updateOrder(
      @PathVariable("id") Long id, @RequestBody OrderJsonRepresentation orderJsonRepresentation) {

    if (orderJsonRepresentation.getId() != null) {
      // to keep PUT as an idempotent call
      LOG.warn("Id must be immutable");
      return ResponseEntity.badRequest().build();
    }
    try {
      boolean updated = orderService.updateOrder(id, orderJsonRepresentation);
      if (updated) {
        return ResponseEntity.ok().build();
      } else {
        LOG.error("Order update for id {} failed", id);
        return ResponseEntity.badRequest().build();
      }
    } catch (ConstraintViolationException | IllegalArgumentException e) {
      LOG.error("Fail: {}", e.toString());
      return ResponseEntity.badRequest().build();
    }
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity deleteOrder(@PathVariable("id") Long id) {
    if (orderService.deleteOrder(id)) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.badRequest().build();
    }
  }
}
