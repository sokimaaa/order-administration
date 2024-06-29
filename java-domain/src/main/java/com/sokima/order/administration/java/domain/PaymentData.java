package com.sokima.order.administration.java.domain;

public final class PaymentData {

    private final PaymentMethod paymentMethod;

    private PaymentData(final PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String paymentMethod() {
        return paymentMethod.name();
    }

    public static PaymentData from(final String paymentMethod) {
        return new PaymentData(PaymentMethod.valueOf(paymentMethod));
    }

    private enum PaymentMethod {
        APPLE_PAY, GOOGLE_PAY, CASH
    }
}
