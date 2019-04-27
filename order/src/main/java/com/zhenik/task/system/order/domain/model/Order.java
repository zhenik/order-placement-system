package com.zhenik.task.system.order.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "orders")
public class Order {
  @Id @GeneratedValue private Long id;
  @NotNull private Long customerId;
  @NotNull private String addressFrom;
  @NotNull private String addressTo;
  @NotNull private String service;
  @NotNull private Long creationDate;
  @NotNull private Long updateDate;
  private String note;

  public Order() {}

  public Order(Long customerId, String addressFrom, String addressTo, String service,
      Long creationDate, Long updateDate, String note) {
    this.customerId = customerId;
    this.addressFrom = addressFrom;
    this.addressTo = addressTo;
    this.service = service;
    this.creationDate = creationDate;
    this.updateDate = updateDate;
    this.note = note;
  }
  public Order(Long id, Long customerId, String addressFrom, String addressTo, String service,
      Long creationDate, Long updateDate, String note) {
    this.id = id;
    this.customerId = customerId;
    this.addressFrom = addressFrom;
    this.addressTo = addressTo;
    this.service = service;
    this.creationDate = creationDate;
    this.updateDate = updateDate;
    this.note = note;
  }


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }

  public String getAddressFrom() {
    return addressFrom;
  }

  public void setAddressFrom(String addressFrom) {
    this.addressFrom = addressFrom;
  }

  public String getAddressTo() {
    return addressTo;
  }

  public void setAddressTo(String addressTo) {
    this.addressTo = addressTo;
  }

  public String getService() {
    return service;
  }

  public void setService(String service) {
    this.service = service;
  }

  public Long getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Long creationDate) {
    this.creationDate = creationDate;
  }

  public Long getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Long updateDate) {
    this.updateDate = updateDate;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  @Override public String toString() {
    return "Order{" +
        "id=" + id +
        ", customerId=" + customerId +
        ", addressFrom='" + addressFrom + '\'' +
        ", addressTo='" + addressTo + '\'' +
        ", service='" + service + '\'' +
        ", creationDate=" + creationDate +
        ", updateDate=" + updateDate +
        ", note='" + note + '\'' +
        '}';
  }
}
