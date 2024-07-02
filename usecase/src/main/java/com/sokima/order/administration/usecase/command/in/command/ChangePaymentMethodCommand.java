package com.sokima.order.administration.usecase.command.in.command;

public record ChangePaymentMethodCommand(
        String orderId,
        String paymentMethod
) {
}
