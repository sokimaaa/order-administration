package com.sokima.order.administration.usecase.query.in;

public record TrackingQuery(
        String orderId,
        String accountID
) {
}
