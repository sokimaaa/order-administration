package com.sokima.order.administration.java.domain.business.id;

import java.util.UUID;

public abstract class GenerateOrderId {
    private GenerateOrderId() {
        throw new IllegalStateException("Utility class");
    }

    public static String generateOrderId() {
        return "ordr_" + UUID.randomUUID();
    }
}
