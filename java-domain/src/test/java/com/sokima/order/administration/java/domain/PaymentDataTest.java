package com.sokima.order.administration.java.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PaymentDataTest {

    @Test
    void validate_validPaymentData_true() {
        var paymentData = PaymentData.from("CASH");
        Assertions.assertTrue(paymentData.validate());
    }
}