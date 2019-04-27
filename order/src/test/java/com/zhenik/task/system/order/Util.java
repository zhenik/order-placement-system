package com.zhenik.task.system.order;

import com.zhenik.task.system.order.domain.representation.CustomerJsonRepresentation;
import com.zhenik.task.system.order.domain.representation.OrderJsonRepresentation;

public class Util {
  static final Long DEFAULT_ID = null;
  static final Long DEFAULT_CUSTOMER_ID = 1L;
  static final String DEFAULT_ADDRESS_FROM = "address from";
  static final String DEFAULT_ADDRESS_TO = "address to";
  static final String DEFAULT_SERVICE = "service like cleaning, packing, moving";
  static final Long DEFAULT_CREATION_DATE = 123456789L;
  static final Long DEFAULT_UPDATE_DATE = DEFAULT_CREATION_DATE;
  static final Long DEFAULT_SERVICE_EVENT_DATE = 1111111L;
  static final String DEFAULT_NOTE = "some default customer note";

  static final String DEFAULT_NAME = "John Doe";
  static final String DEFAULT_PHONE = "93973845";
  static final String DEFAULT_EMAIL = "example@example.com";

  public static CustomerJsonRepresentation getCustomerJsonRepresentation() {
    return new CustomerJsonRepresentation(DEFAULT_CUSTOMER_ID, DEFAULT_NAME, DEFAULT_PHONE, DEFAULT_EMAIL);
  }

  public static OrderJsonRepresentation getOrder() {
    return new OrderJsonRepresentation(
        DEFAULT_ID,
        DEFAULT_CUSTOMER_ID,
        DEFAULT_ADDRESS_FROM,
        DEFAULT_ADDRESS_TO,
        DEFAULT_SERVICE,
        DEFAULT_CREATION_DATE,
        DEFAULT_UPDATE_DATE,
        DEFAULT_SERVICE_EVENT_DATE,
        DEFAULT_NOTE
    );
  }
}
