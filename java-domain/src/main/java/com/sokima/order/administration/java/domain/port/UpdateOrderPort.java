package com.sokima.order.administration.java.domain.port;

import com.sokima.order.administration.java.domain.Order;

public interface UpdateOrderPort {
    Order updateOrder(final Order order);
}
