package com.sokima.order.administration.java.domain.port.out;

import com.sokima.order.administration.java.domain.Order;

import java.util.Optional;

public interface FindOrderOutPort {
    Optional<Order> findOrderById(final String orderId);
}
