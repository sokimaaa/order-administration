package com.sokima.order.administration.usecase.out.event;

import com.sokima.order.administration.java.domain.port.out.event.Sendable;

public record OrderConfirmedEvent() implements Sendable {
    @Override
    public String getEventName() {
        return "order-confirmed";
    }
}
