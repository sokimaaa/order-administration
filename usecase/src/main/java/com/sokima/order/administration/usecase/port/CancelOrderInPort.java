package com.sokima.order.administration.usecase.port;

import com.sokima.order.administration.usecase.command.in.CancelOrderCommand;

public interface CancelOrderInPort {
    void cancelOrder(final CancelOrderCommand cancelOrderCommand);
}
