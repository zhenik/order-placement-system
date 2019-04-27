package com.zhenik.task.system.order.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
  @Id
  @GeneratedValue
  private Long id;
  private String name;
  private String description;

  public Order() { }
  public Order(String name, String description) {
    this.name = name;
    this.description = description;
  }
  public Order(Long id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
  }



  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }

  @Override public String toString() {
    return "Order{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        '}';
  }
}
