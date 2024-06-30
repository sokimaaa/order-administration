package com.sokima.order.administration.usecase.port;

import com.sokima.order.administration.usecase.in.command.UpdateProductCommand;

public interface ModifyProductInPort {
    void modifyProduct(final UpdateProductCommand updateProductCommand);
}
