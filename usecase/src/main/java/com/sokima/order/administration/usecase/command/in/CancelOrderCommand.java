package com.sokima.order.administration.usecase.command.in;

public record CancelOrderCommand(
        String orderId,
        String reason
) {
}
