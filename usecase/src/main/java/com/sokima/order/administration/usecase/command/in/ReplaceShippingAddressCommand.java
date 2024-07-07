package com.sokima.order.administration.usecase.command.in;

public record ReplaceShippingAddressCommand(
        String orderId,
        String shippingAddress
) {
}
