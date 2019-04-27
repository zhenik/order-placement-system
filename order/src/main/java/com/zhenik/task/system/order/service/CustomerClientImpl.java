package com.zhenik.task.system.order.service;

import com.zhenik.task.system.order.domain.representation.CustomerJsonRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class CustomerClientImpl {
  private static Logger LOG = LoggerFactory.getLogger(CustomerClientImpl.class);
  private RestTemplate restTemplate;
  private String baseCustomerUrl;

  public CustomerClientImpl(RestTemplate restTemplate, String baseCustomerUrl) {
    this.restTemplate = restTemplate;
    this.baseCustomerUrl = baseCustomerUrl;
    LOG.info("Base customer URL {}", baseCustomerUrl);
  }

  public boolean isCustomerExist(Long customerId) {
    String URL = baseCustomerUrl + String.format("/customers/%s", customerId);
    try {
      ResponseEntity<CustomerJsonRepresentation> responseEntity = restTemplate.getForEntity(URL, CustomerJsonRepresentation.class);
      return responseEntity.getStatusCode().equals(HttpStatus.OK);
    } catch (HttpClientErrorException errorException) {
        errorException.printStackTrace();
        LOG.debug("Request failed {}", errorException.toString());
        return false;
    }
  }
}
