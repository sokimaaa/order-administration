package com.sokima.order.administration.java.domain.port.out;

import com.sokima.order.administration.java.domain.Order;

import java.util.Optional;
import java.util.Set;

public interface FindOrderOutPort {
    Optional<Order> findOrderById(final String orderId);

    Set<Order> findOrdersByAccountId(final String accountId);
}
