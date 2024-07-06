package com.sokima.order.administration.java.domain;

import com.sokima.order.administration.java.domain.business.validate.Validatable;

import java.util.Objects;

public record Order(
        String orderId,
        String accountId,
        Status status,
        Products products,
        DeliveryData deliveryData,
        PaymentData paymentData
) implements Validatable {
    public Order applyDeltaProducts(final DeltaProducts deltaProducts) {
        final var newProducts = this.products.delta(deltaProducts);
        return new Order(
                this.orderId,
                this.accountId,
                this.status,
                newProducts,
                this.deliveryData,
                this.paymentData
        );
    }

    public Order updatePaymentData(final PaymentData newPaymentData) {
        return new Order(
                this.orderId,
                this.accountId,
                this.status,
                this.products,
                this.deliveryData,
                newPaymentData
        );
    }

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

    public Order updateShippingAddress(final String newShippingAddress) {
        return new Order(
                this.orderId,
                this.accountId,
                this.status,
                this.products,
                deliveryData.withShippingAddress(newShippingAddress),
                this.paymentData
        );
    }

    @Override
    public boolean validate() {
        return Objects.nonNull(orderId) &&
                Objects.nonNull(accountId) && !accountId.isBlank() &&
                Objects.nonNull(status) &&
                Objects.nonNull(products) && products.validate() &&
                Objects.nonNull(deliveryData) && deliveryData.validate() &&
                Objects.nonNull(paymentData) && paymentData.validate()
                ;
    }
}
