package com.sokima.order.administration.java.domain.port;

import com.sokima.order.administration.java.domain.Order;

public interface SaveOrderPort {
    Order saveOrder(final Order order);
}
