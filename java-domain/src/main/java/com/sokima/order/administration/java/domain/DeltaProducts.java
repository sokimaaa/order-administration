package com.sokima.order.administration.java.domain;

import java.util.Set;

public record DeltaProducts(Set<String> productIds, Float amount) {
    public static DeltaProducts from(Set<String> productIds, Float amount) {
        return new DeltaProducts(productIds, amount);
    }
}
