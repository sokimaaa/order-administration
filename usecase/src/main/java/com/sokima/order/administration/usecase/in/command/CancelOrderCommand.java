package com.sokima.order.administration.usecase.in.command;

public record CancelOrderCommand(
        Integer orderId,
        String reason
) {
}
