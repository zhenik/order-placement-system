package com.zhenik.task.system.order.domain.representation;

import java.io.Serializable;

public class OrderJsonRepresentation implements Serializable {
  private Long id;
  private Long customerId;
  private String addressFrom;
  private String addressTo;
  private String service;
  private Long creationDate;
  private Long updateDate;
  private Long serviceEventDate;
  private String note;

  public OrderJsonRepresentation() { }
  public OrderJsonRepresentation(Long id, Long customerId, String addressFrom,
      String addressTo, String service, Long creationDate, Long updateDate, Long serviceEventDate, String note) {
    this.id = id;
    this.customerId = customerId;
    this.addressFrom = addressFrom;
    this.addressTo = addressTo;
    this.service = service;
    this.creationDate = creationDate;
    this.updateDate = updateDate;
    this.serviceEventDate = serviceEventDate;
    this.note = note;
  }
  public OrderJsonRepresentation(Long customerId, String addressFrom, String addressTo,
      String service, Long creationDate, Long updateDate, Long serviceEventDate, String note) {
    this.customerId = customerId;
    this.addressFrom = addressFrom;
    this.addressTo = addressTo;
    this.service = service;
    this.creationDate = creationDate;
    this.updateDate = updateDate;
    this.serviceEventDate = serviceEventDate;
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

  public Long getServiceEventDate() {
    return serviceEventDate;
  }

  public void setServiceEventDate(Long serviceEventDate) {
    this.serviceEventDate = serviceEventDate;
  }

  @Override public String toString() {
    return "OrderJsonRepresentation{" +
        "id=" + id +
        ", customerId=" + customerId +
        ", addressFrom='" + addressFrom + '\'' +
        ", addressTo='" + addressTo + '\'' +
        ", service='" + service + '\'' +
        ", creationDate=" + creationDate +
        ", updateDate=" + updateDate +
        ", serviceEventDate=" + serviceEventDate +
        ", note='" + note + '\'' +
        '}';
  }
}
