package com.sokima.order.administration.java.domain;

import com.sokima.order.administration.java.domain.business.validate.Validatable;

import java.util.Objects;

public final class PaymentData implements Validatable {

    private final PaymentMethod paymentMethod;

    private PaymentData(final PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public boolean validate() {
        return Objects.nonNull(paymentMethod);
    }

    public String paymentMethod() {
        return paymentMethod.name();
    }

    public static PaymentData from(final String paymentMethod) {
        try {
            return new PaymentData(PaymentMethod.valueOf(paymentMethod));
        } catch (IllegalArgumentException | NullPointerException e) {
            return new PaymentData(null);
        }
    }

    private enum PaymentMethod {
        APPLE_PAY, GOOGLE_PAY, CASH
    }
}
