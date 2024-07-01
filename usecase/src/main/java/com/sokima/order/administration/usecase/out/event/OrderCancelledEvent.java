package com.sokima.order.administration.usecase.out.event;

import com.sokima.order.administration.java.domain.port.out.event.Sendable;

public record OrderCancelledEvent() implements Sendable {
    @Override
    public String getEventName() {
        return "order-cancelled";
    }
}
