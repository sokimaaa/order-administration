package com.sokima.order.administration.usecase.port;

import com.sokima.order.administration.usecase.in.command.CancelOrderCommand;

public interface CancelOrderInPort {
    void cancelOrder(final CancelOrderCommand cancelOrderCommand);
}
