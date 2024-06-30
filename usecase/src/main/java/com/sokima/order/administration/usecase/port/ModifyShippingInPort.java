package com.sokima.order.administration.usecase.port;

import com.sokima.order.administration.usecase.in.command.ReplaceShippingAddressCommand;

public interface ModifyShippingInPort {
    void modifyShipping(final ReplaceShippingAddressCommand shippingAddressCommand);
}
