package com.sokima.order.administration.java.domain.business;

import com.sokima.order.administration.java.domain.Order;

public interface OnStatus {
    Order doOnStatus(final Order order);

    String status();
}
