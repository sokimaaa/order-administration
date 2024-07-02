package com.sokima.order.administration.java.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryDataTest {

    @Test
    void validate_validDeliveryData_true() {
        var deliveryData = new DeliveryData("test address", null);
        Assertions.assertTrue(deliveryData.validate());
    }

    @Test
    void validate_invalidDeliveryData_false() {
        var deliveryData = new DeliveryData(null, null);
        Assertions.assertFalse(deliveryData.validate());
    }
}