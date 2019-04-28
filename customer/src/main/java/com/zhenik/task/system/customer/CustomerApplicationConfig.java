package com.zhenik.task.system.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.uber.jaeger.samplers.ProbabilisticSampler;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableJpaRepositories(basePackages = ("com.zhenik.task.system.customer.repository"))
@EntityScan(basePackages = ("com.zhenik.task.system.customer"))
@EnableSwagger2
public class CustomerApplicationConfig {

  @Bean(name = "OBJECT_MAPPER_BEAN")
  public ObjectMapper jsonObjectMapper() {
    return Jackson2ObjectMapperBuilder.json()
        .serializationInclusion(JsonInclude.Include.NON_NULL)
        .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .build();
  }

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.zhenik.task.system.customer.controller"))
        .paths(PathSelectors.any())
        .build()
        .apiInfo(
            new ApiInfoBuilder()
                .title("API for customer service")
                .description("Micro-service for running customer management")
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

    return new com.uber.jaeger.Configuration("customer-service", samplerConfiguration, reporterConfiguration)
        .getTracer();
  }

  @Bean
  @Profile("!docker")
  public io.opentracing.Tracer jaegerTracerDefault() {

    System.out.println("LOADED DEFAULT CONFIG");

    return new com.uber.jaeger.Configuration(
        "customer-service",
        new com.uber.jaeger.Configuration.SamplerConfiguration(ProbabilisticSampler.TYPE, 1),
        new com.uber.jaeger.Configuration.ReporterConfiguration())
        .getTracer();
  }
}
