package com.sokima.order.administration.usecase.command.in;

public record ChangePaymentMethodCommand(
        String orderId,
        String paymentMethod
) {
}
