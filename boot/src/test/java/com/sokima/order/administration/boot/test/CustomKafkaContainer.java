package com.sokima.order.administration.boot.test;

import org.testcontainers.containers.Network;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

public class CustomKafkaContainer extends KafkaContainer {

    private static final String KAFKA_IMAGE = "confluentinc/cp-kafka:7.4.0";

    public static KafkaContainer createKafkaContainer() {
        return new CustomKafkaContainer()
                .withEnv();
    }

    private CustomKafkaContainer() {
        this(DockerImageName.parse(KAFKA_IMAGE));
    }

    private CustomKafkaContainer(final DockerImageName dockerImageName) {
        super(dockerImageName);
    }

    private CustomKafkaContainer withEnv() {
        this.withNetwork(Network.newNetwork());
        this.withEnv("KAFKA_TRANSACTION_STATE_LOG_MIN_ISR", "1");
        this.withEnv("KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR", "1");
        this.withKraft();
        return (CustomKafkaContainer) self();
    }

    @Override
    public void start() {
        super.start();
    }
}
