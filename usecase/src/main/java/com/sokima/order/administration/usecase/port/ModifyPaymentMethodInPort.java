package com.sokima.order.administration.usecase.port;

import com.sokima.order.administration.usecase.command.in.command.ChangePaymentMethodCommand;

public interface ModifyPaymentMethodInPort {
    void modifyPaymentMethod(final ChangePaymentMethodCommand changePaymentMethodCommand);
}
