package com.sokima.order.administration.java.domain.port.out;

import com.sokima.order.administration.java.domain.Order;

public interface UpdateOrderOutPort {
    Order updateOrder(final Order order);
}
