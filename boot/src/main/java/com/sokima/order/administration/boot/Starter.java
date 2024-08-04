package com.sokima.order.administration.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.sokima.order.administration.infrastructure")
@EntityScan("com.sokima.order.administration.infrastructure.driven.persistence")
@EnableJpaRepositories("com.sokima.order.administration.infrastructure.driven.persistence")
public class Starter {

  public static void main(String[] args) {
    SpringApplication.run(Starter.class, args);
  }
}
