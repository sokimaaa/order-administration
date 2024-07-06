package com.sokima.order.administration.java.domain;

import com.google.common.collect.Sets;
import com.sokima.order.administration.java.domain.business.validate.Validatable;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public final class Products implements Validatable {

    private final Set<String> productIds;
    private final Float amount;

    private Products(final Set<String> productIds, final Float amount) {
        this.productIds = Optional.ofNullable(productIds).orElse(Set.of());
        this.amount = Optional.ofNullable(amount).orElse(0.f);
    }

    public static Products from(final Set<String> productIds, final Float amount) {
        return new Products(productIds, amount);
    }

    @Override
    public boolean validate() {
        return Objects.nonNull(productIds) && !productIds.isEmpty()
                && Objects.nonNull(amount) && amount > 0.f;
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
