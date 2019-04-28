package com.zhenik.task.system.e2e;

import com.zhenik.task.system.e2e.representations.CustomerJsonRepresentation;
import com.zhenik.task.system.e2e.representations.OrderJsonRepresentation;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CustomerIT extends BaseIT {

  @Test
  public void register_new_customer() {
    CustomerJsonRepresentation customerJsonPresentation = Util.getCustomerJsonRepresentation();
    Long id = registerCustomer(customerJsonPresentation);
    assertNotNull(id);

    CustomerJsonRepresentation customerFetched = getCustomer(id);
    assertEquals(customerJsonPresentation.getEmail(), customerFetched.getEmail());
  }

  @Test
  public void place_new_order() {
    CustomerJsonRepresentation customerJsonPresentation = Util.getCustomerJsonRepresentation();
    Long customerId = registerCustomer(customerJsonPresentation);
    assertNotNull(customerId);

    OrderJsonRepresentation orderJsonRepresentation = Util.getOrderJsonRepresentation(customerId);

    Long orderId = placeNewOrder(orderJsonRepresentation);
    assertNotNull(orderId);
  }

  @Test
  public void update_order_change_customer_and_note() {
    // register 2 customers
    CustomerJsonRepresentation customerJsonPresentation1 = Util.getCustomerJsonRepresentation();
    CustomerJsonRepresentation customerJsonPresentation2 = Util.getCustomerJsonRepresentation();
    Long customerId1 = registerCustomer(customerJsonPresentation1);
    Long customerId2 = registerCustomer(customerJsonPresentation2);
    assertNotNull(customerId1);
    assertNotNull(customerId2);

    // create order for customerId1
    OrderJsonRepresentation orderJsonRepresentation = Util.getOrderJsonRepresentation(customerId1);
    Long orderId = placeNewOrder(orderJsonRepresentation);
    assertNotNull(orderId);

    OrderJsonRepresentation orderJsonUpdate = Util.getOrderJsonRepresentation(customerId2);
    orderJsonUpdate.setAddressFrom("A");
    orderJsonUpdate.setAddressTo("B");
    orderJsonUpdate.setNote("new customer");

    // update
    updateOrder(orderId, orderJsonUpdate);

    // fetch update order
    OrderJsonRepresentation orderUpdateFetched = getOrder(orderId);

    assertEquals("A", orderUpdateFetched.getAddressFrom());
    assertEquals("B", orderUpdateFetched.getAddressTo());
  }
}
