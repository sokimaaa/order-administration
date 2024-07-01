package com.sokima.order.administration.java.domain;

import com.google.common.collect.Sets;

import java.util.Set;

public final class Products {

    private final Set<String> productIds;
    private final Float amount;

    private Products(final Set<String> productIds, final Float amount) {
        this.productIds = productIds;
        this.amount = amount;
    }

    public static Products from(final Set<String> productIds, final Float amount) {
        return new Products(productIds, amount);
    }

    public Products delta(final DeltaProducts that) {
        return new Products(
                Sets.symmetricDifference(this.productIds, that.productIds()),
                this.amount - that.amount()
        );
    }

    public Set<String> getProductIds() {
        return productIds;
    }

    public Float getAmount() {
        return amount;
    }
}
