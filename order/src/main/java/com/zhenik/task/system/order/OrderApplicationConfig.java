package com.zhenik.task.system.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.uber.jaeger.samplers.ProbabilisticSampler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableJpaRepositories(basePackages = ("com.zhenik.task.system.order"))
@EntityScan(basePackages = ("com.zhenik.task.system.order"))
@EnableSwagger2
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
  public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
    return restTemplateBuilder.build();
  }

  @Bean
  public String getBaseCustomerUrl() {
    return baseCustomerUrl;
  }

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.zhenik.task.system.order.controller"))
        .paths(PathSelectors.any())
        .build()
        .apiInfo(
            new ApiInfoBuilder()
                .title("API for order service")
                .description("Micro-service for running order placement management")
                .version("1.0")
                .build());
  }

  @Bean
  @Profile("docker")
  public io.opentracing.Tracer jaegerTracer() {
    final com.uber.jaeger.Configuration.SamplerConfiguration samplerConfiguration =
        new com.uber.jaeger.Configuration.SamplerConfiguration(
            ProbabilisticSampler.TYPE, 1, "jaeger:5778");

    final com.uber.jaeger.Configuration.ReporterConfiguration reporterConfiguration =
        new com.uber.jaeger.Configuration.ReporterConfiguration(
            null, "jaeger", 6831, null, null
        );

    return new com.uber.jaeger.Configuration("order-service", samplerConfiguration, reporterConfiguration)
        .getTracer();
  }

  @Bean
  @Profile("!docker")
  public io.opentracing.Tracer jaegerTracerDefault() {
    return new com.uber.jaeger.Configuration(
            "order-service",
            new com.uber.jaeger.Configuration.SamplerConfiguration(ProbabilisticSampler.TYPE, 1),
            new com.uber.jaeger.Configuration.ReporterConfiguration())
        .getTracer();
  }
}
