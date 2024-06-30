package com.sokima.order.administration.java.domain.port;

import com.sokima.order.administration.java.domain.Order;

import java.util.Optional;

public interface FindOrderPort {
    Optional<Order> findOrderById(final String orderId);
}
