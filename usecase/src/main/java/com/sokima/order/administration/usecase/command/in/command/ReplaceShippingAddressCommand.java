package com.sokima.order.administration.usecase.command.in.command;

public record ReplaceShippingAddressCommand(
        Integer orderId,
        String shippingAddress
) {
}
