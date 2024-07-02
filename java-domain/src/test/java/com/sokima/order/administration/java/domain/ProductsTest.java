package com.sokima.order.administration.java.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProductsTest {

    @Test
    void validate_validProducts_true() {
        var products = Products.from(Set.of("1"), 1.0f);
        Assertions.assertTrue(products.validate());
    }

    @Test
    void validate_emptyProductSet_false() {
        var products = Products.from(Set.of(), 1.0f);
        Assertions.assertFalse(products.validate());
    }

    @Test
    void validate_nullProductSet_false() {
        var products = Products.from(null, 1.0f);
        Assertions.assertFalse(products.validate());
    }

    @Test
    void validate_invalidAmount_false() {
        var products = Products.from(Set.of("1"), -1.0f);
        Assertions.assertFalse(products.validate());
    }

    @Test
    void validate_nullAmount_false() {
        var products = Products.from(Set.of("1"), null);
        Assertions.assertFalse(products.validate());
    }

    @Test
    void delta_twoSameProducts_emptyProducts() {
        var firstProduct = Products.from(Set.of("1", "2", "3"), 1.0f);
        var secondProduct = DeltaProducts.from(Set.of("2", "1", "3"), 1.0f);

        var actualDelta = firstProduct.delta(secondProduct);

        Assertions.assertNotNull(actualDelta);
        Assertions.assertTrue(actualDelta.getProductIds().isEmpty());
        Assertions.assertEquals(0.f, actualDelta.getAmount());
    }

    @Test
    void delta_twoDifferentProducts_mergedProducts() {
        var firstProduct = Products.from(Set.of("1", "2", "3"), 1.0f);
        var secondProduct = DeltaProducts.from(Set.of("4", "5", "6"), -3.0f);

        var actualDelta = firstProduct.delta(secondProduct);

        Assertions.assertNotNull(actualDelta);
        Assertions.assertFalse(actualDelta.getProductIds().isEmpty());
        Assertions.assertEquals(6,  actualDelta.getProductIds().size());
        Assertions.assertEquals(4.f, actualDelta.getAmount());
    }

    @Test
    void delta_twoSemiDifferentProducts_skippedSameProducts() {
        var firstProduct = Products.from(Set.of("1", "2", "3"), 1.0f);
        var secondProduct = DeltaProducts.from(Set.of("4", "1", "2"), 0.2f);

        var actualDelta = firstProduct.delta(secondProduct);

        Assertions.assertNotNull(actualDelta);
        Assertions.assertFalse(actualDelta.getProductIds().isEmpty());
        Assertions.assertEquals(2,  actualDelta.getProductIds().size());
        Assertions.assertFalse(actualDelta.getProductIds().contains("1"));
        Assertions.assertTrue(actualDelta.getProductIds().contains("4"));
        Assertions.assertEquals(0.8f, actualDelta.getAmount());
    }
}