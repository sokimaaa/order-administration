package com.sokima.order.administration.usecase.command.out;

import com.sokima.order.administration.java.domain.port.out.event.Sendable;

public record OrderConfirmedEvent(
        String orderId
) implements Sendable {
    @Override
    public String getEventName() {
        return "order-confirmed";
    }
}
