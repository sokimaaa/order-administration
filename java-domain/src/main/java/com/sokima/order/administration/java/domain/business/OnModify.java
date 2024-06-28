package com.sokima.order.administration.java.domain.business;

import com.sokima.order.administration.java.domain.Order;

public interface OnModify {

    Order doOnModify(final Order order);

    String type();
}
