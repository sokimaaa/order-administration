package com.sokima.order.administration.usecase.in.command;

public record ReplaceShippingAddressCommand(
        Integer orderId,
        String shippingAddress
) {
}
