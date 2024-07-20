package com.sokima.order.administration.usecase.query.out;

import com.sokima.order.administration.java.domain.Order;

import java.util.SortedSet;

public record OrderHistoryResponse(
        SortedSet<Order> orders,
        String accountId,
        int count
) {
    public OrderHistoryResponse(final SortedSet<Order> orders, final String accountId) {
        this(orders, accountId, orders.size());
    }
}
