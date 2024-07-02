package com.sokima.order.administration.usecase.port;

import com.sokima.order.administration.usecase.command.in.command.PlaceOrderCommand;

public interface PlaceOrderInPort {
    void placeOrder(final PlaceOrderCommand placeOrderCommand);
}
