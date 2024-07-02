package com.sokima.order.administration.usecase.command.in.command;

import java.util.Set;

public record PlaceOrderCommand(
        Integer orderId,
        String accountId,
        Set<String> productIds,
        String shippingAddress,
        String productProfile,
        Float amount,
        String paymentMethod
) {
}
