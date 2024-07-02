package com.sokima.order.administration.usecase.command.in.command;

public record CancelOrderCommand(
        String orderId,
        String reason
) {
}
