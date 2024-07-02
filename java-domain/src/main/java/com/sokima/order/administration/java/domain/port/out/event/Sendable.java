package com.sokima.order.administration.java.domain.port.out.event;

public interface Sendable {
    String orderId();

    String getEventName();
}
