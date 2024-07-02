package com.sokima.order.administration.usecase.command.in.command;

public record ReplaceShippingAddressCommand(
        String orderId,
        String shippingAddress
) {
}
