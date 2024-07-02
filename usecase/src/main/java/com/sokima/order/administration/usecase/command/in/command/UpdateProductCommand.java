package com.sokima.order.administration.usecase.command.in.command;

import java.util.Set;

public record UpdateProductCommand(
        Integer orderId,
        Set<String> deltaProductIds,
        Float deltaAmount,
        String deltaProductProfile
) {
}
