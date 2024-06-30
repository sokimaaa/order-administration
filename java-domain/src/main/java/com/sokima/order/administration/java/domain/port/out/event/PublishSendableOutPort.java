package com.sokima.order.administration.java.domain.port.out.event;

public interface PublishSendableOutPort {
    void publish(final Sendable sendable);
}
