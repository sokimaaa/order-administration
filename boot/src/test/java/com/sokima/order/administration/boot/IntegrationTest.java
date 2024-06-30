package com.sokima.order.administration.boot;

import com.sokima.order.administration.boot.test.CustomKafkaContainer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(classes = Starter.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@Testcontainers
public abstract class IntegrationTest {

    @Container
    protected final static KafkaContainer KAFKA = CustomKafkaContainer.createKafkaContainer();
}
