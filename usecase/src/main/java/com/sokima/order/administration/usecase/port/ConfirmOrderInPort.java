package com.sokima.order.administration.usecase.port;

import com.sokima.order.administration.usecase.in.command.ConfirmOrderCommand;

public interface ConfirmOrderInPort {
    void confirmOrder(final ConfirmOrderCommand confirmOrderCommand);
}
