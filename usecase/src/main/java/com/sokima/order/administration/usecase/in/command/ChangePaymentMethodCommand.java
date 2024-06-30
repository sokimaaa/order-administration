package com.sokima.order.administration.usecase.in.command;

public record ChangePaymentMethodCommand(
        Integer orderId,
        String paymentMethod
) {
}
