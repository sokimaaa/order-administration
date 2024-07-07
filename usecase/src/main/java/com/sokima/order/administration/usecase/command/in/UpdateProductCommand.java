package com.sokima.order.administration.usecase.command.in;

import java.util.Set;

public record UpdateProductCommand(
        String orderId,
        Set<String> deltaProductIds,
        Float deltaAmount,
        String deltaProductProfile
) {
}
