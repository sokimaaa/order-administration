package com.sokima.order.administration.java.domain;

import com.sokima.order.administration.java.domain.business.validate.Validatable;

import java.util.Objects;

public record DeliveryData(
        String shippingAddress,
        String packageDimension
) implements Validatable {

    public DeliveryData(final String shippingAddress) {
        this(shippingAddress, null);
    }

    @Override
    public boolean validate() {
        return Objects.nonNull(shippingAddress);
    }
}
