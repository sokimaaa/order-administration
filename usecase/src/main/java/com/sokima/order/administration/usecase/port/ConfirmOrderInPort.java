package com.sokima.order.administration.usecase.port;

import com.sokima.order.administration.usecase.command.in.command.ConfirmOrderCommand;

public interface ConfirmOrderInPort {
    void confirmOrder(final ConfirmOrderCommand confirmOrderCommand);
}
