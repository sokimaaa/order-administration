package com.sokima.order.administration.usecase.command.out.event;

import com.sokima.order.administration.java.domain.port.out.event.Sendable;

public record OrderCancelledEvent(
        String orderId
) implements Sendable {
    @Override
    public String getEventName() {
        return "order-cancelled";
    }
}
