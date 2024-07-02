package com.sokima.order.administration.usecase.command.out.event;

import com.sokima.order.administration.java.domain.port.out.event.Sendable;

public record OrderInfoUpdatedEvent(
        Integer orderId
) implements Sendable {
    @Override
    public String getEventName() {
        return "order-info-updated";
    }
}
