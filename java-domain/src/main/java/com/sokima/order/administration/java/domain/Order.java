package com.sokima.order.administration.java.domain;

public record Order(
        Integer orderId,
        String accountId,
        Status status,
        Products products,
        DeliveryData deliveryData,
        PaymentData paymentData
) {
    public Order updateStatus(final Status newStatus) {
        return new Order(
                this.orderId,
                this.accountId,
                newStatus,
                this.products,
                this.deliveryData,
                this.paymentData
        );
    }
}
