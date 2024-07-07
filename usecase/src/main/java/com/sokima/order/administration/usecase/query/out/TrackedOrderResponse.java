package com.sokima.order.administration.usecase.query.out;

import com.sokima.order.administration.java.domain.Order;

public record TrackedOrderResponse(
        Order order,
        String description
) {
}
