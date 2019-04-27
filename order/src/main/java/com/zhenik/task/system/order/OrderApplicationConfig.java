package com.zhenik.task.system.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableJpaRepositories(basePackages = ("com.zhenik.task.system.order"))
@EntityScan(basePackages = ("com.zhenik.task.system.order"))
public class OrderApplicationConfig {

  @Value(value = "${customer.uri}")
  private String baseCustomerUrl;

  @Bean(name = "OBJECT_MAPPER_BEAN")
  public ObjectMapper jsonObjectMapper() {
    return Jackson2ObjectMapperBuilder.json()
        .serializationInclusion(JsonInclude.Include.NON_NULL)
        .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .build();
  }

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder)  {
    return restTemplateBuilder.build();
  }

  @Bean
  public String getBaseCustomerUrl() {
    return baseCustomerUrl;
  }
}
