package com.sokima.order.administration.usecase.port;

import com.sokima.order.administration.usecase.command.in.ReplaceShippingAddressCommand;

public interface ModifyShippingInPort {
    void modifyShipping(final ReplaceShippingAddressCommand shippingAddressCommand);
}
