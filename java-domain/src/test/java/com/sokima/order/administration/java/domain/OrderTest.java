package com.sokima.order.administration.java.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderTest {

    @Mock Products products;
    @Mock DeliveryData deliveryData;
    @Mock PaymentData paymentData;

    @Test
    void validate_validOrder_true() {
        Mockito.when(products.validate()).thenReturn(true);
        Mockito.when(deliveryData.validate()).thenReturn(true);
        Mockito.when(paymentData.validate()).thenReturn(true);

        var order = new Order("123", "123", Status.APPROVED, products, deliveryData, paymentData);
        Assertions.assertTrue(order.validate());
    }

    @Test
    void validate_invalidProducts_false() {
        Mockito.when(products.validate()).thenReturn(false);
        var order = new Order("123", "123", Status.APPROVED, products, deliveryData, paymentData);
        Assertions.assertFalse(order.validate());
    }

    @Test
    void validate_nullProducts_false() {
        var order = new Order("123", "123", Status.APPROVED, null, deliveryData, paymentData);
        Assertions.assertFalse(order.validate());
    }

    @Test
    void validate_invalidDeliveryData_false() {
        Mockito.when(products.validate()).thenReturn(true);
        Mockito.when(deliveryData.validate()).thenReturn(false);
        var order = new Order("123", "123", Status.APPROVED, products, deliveryData, paymentData);
        Assertions.assertFalse(order.validate());
    }

    @Test
    void validate_nullDeliveryData_false() {
        Mockito.when(products.validate()).thenReturn(true);
        var order = new Order("123", "123", Status.APPROVED, products, null, paymentData);
        Assertions.assertFalse(order.validate());
    }

    @Test
    void validate_invalidPaymentData_false() {
        Mockito.when(products.validate()).thenReturn(true);
        Mockito.when(deliveryData.validate()).thenReturn(true);
        Mockito.when(paymentData.validate()).thenReturn(false);

        var order = new Order("123", "123", Status.APPROVED, products, deliveryData, paymentData);
        Assertions.assertFalse(order.validate());
    }

    @Test
    void validate_nullPaymentData_false() {
        Mockito.when(products.validate()).thenReturn(true);
        Mockito.when(deliveryData.validate()).thenReturn(true);

        var order = new Order("123", "123", Status.APPROVED, products, deliveryData, null);
        Assertions.assertFalse(order.validate());
    }

    @Test
    void validate_invalidOrderStatus_false() {
        var order = new Order("123", "123", null, products, deliveryData, paymentData);
        Assertions.assertFalse(order.validate());
    }

    @ParameterizedTest
    @EmptySource
    @NullSource
    void validate_invalidAccountId_false(final String accountId) {
        var order = new Order("123", accountId, Status.APPROVED, products, deliveryData, paymentData);
        Assertions.assertFalse(order.validate());
    }

    @Test
    void validate_invalidAccountId_false() {
        var order = new Order(null, "123", Status.APPROVED, products, deliveryData, paymentData);
        Assertions.assertFalse(order.validate());
    }
}