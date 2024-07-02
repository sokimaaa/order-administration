package com.sokima.order.administration.usecase.command.in.command;

public record CancelOrderCommand(
        Integer orderId,
        String reason
) {
}
