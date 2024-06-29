package com.sokima.order.administration.java.domain;

public record Order(
        Integer orderId,
        String accountId,
        Status status,
        Products products,
        DeliveryData deliveryData,
        PaymentData paymentData
) {
}
