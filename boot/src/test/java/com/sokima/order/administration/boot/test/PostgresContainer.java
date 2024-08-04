package com.sokima.order.administration.boot.test;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public interface PostgresContainer {
  @Container
  PostgreSQLContainer<?> postgreSqlContainer = createPostgresContainer();

  @DynamicPropertySource
  static void configureProperties(final DynamicPropertyRegistry registry) {
    registry.add("POSTGRES_HOST", postgreSqlContainer::getHost);
    registry.add("POSTGRES_PORT", postgreSqlContainer::getFirstMappedPort);
    registry.add("POSTGRES_USERNAME", postgreSqlContainer::getUsername);
    registry.add("POSTGRES_PASSWORD", postgreSqlContainer::getPassword);
  }

  static PostgreSQLContainer<?> createPostgresContainer() {
    return new PostgreSQLContainer<>(DockerImageName.parse("postgres:14.1"))
        .withDatabaseName("orders")
        .withUsername("postgres")
        .withPassword("postgres");
  }
}
