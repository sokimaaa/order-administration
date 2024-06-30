package com.sokima.order.administration.usecase.port;

import com.sokima.order.administration.usecase.in.command.PlaceOrderCommand;

public interface PlaceOrderInPort {
    void placeOrder(final PlaceOrderCommand placeOrderCommand);
}
