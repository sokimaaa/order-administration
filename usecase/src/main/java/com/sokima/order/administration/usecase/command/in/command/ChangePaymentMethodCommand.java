package com.sokima.order.administration.usecase.command.in.command;

public record ChangePaymentMethodCommand(
        Integer orderId,
        String paymentMethod
) {
}
