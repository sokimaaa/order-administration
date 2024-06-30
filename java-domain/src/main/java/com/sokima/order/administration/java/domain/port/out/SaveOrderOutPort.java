package com.sokima.order.administration.java.domain.port.out;

import com.sokima.order.administration.java.domain.Order;

public interface SaveOrderOutPort {
    Order saveOrder(final Order order);
}
