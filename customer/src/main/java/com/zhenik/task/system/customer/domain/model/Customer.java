package com.zhenik.task.system.customer.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customers")
public class Customer {
  @Id @GeneratedValue private Long id;
  private String name;
  private String phoneNumber;
  private String email;

  public Customer() {}

  public Customer(String name, String phoneNumber, String email) {
    this.name = name;
    this.phoneNumber = phoneNumber;
    this.email = email;
  }

  public Customer(Long id, String name, String phoneNumber, String email) {
    this.id = id;
    this.name = name;
    this.phoneNumber = phoneNumber;
    this.email = email;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    return "Customer{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", phoneNumber='"
        + phoneNumber
        + '\''
        + ", email='"
        + email
        + '\''
        + '}';
  }
}
