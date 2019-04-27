package com.zhenik.task.system.order.service;

import com.zhenik.task.system.order.domain.representation.CustomerJsonRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class CustomerClientImpl {
  private RestTemplate restTemplate;
  @Value(value = "${customer.uri}")
  private String baseCustomerUrl;

  public CustomerClientImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public boolean isCustomerExist(Long customerId) {
    String URL = baseCustomerUrl + String.format("/customers/%s", customerId);
    try {
      ResponseEntity<CustomerJsonRepresentation> responseEntity =
          restTemplate.getForEntity(URL, CustomerJsonRepresentation.class);
      return responseEntity.getStatusCode().equals(HttpStatus.OK);
    } catch (HttpClientErrorException errorException) {
      return false;
    }
  }

}
