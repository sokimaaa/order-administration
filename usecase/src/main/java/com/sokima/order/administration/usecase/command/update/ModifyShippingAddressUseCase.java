package com.sokima.order.administration.usecase.command.update;

import com.sokima.order.administration.java.domain.business.operation.UpdateOrderOperation;
import com.sokima.order.administration.java.domain.business.operation.in.OperationContext;
import com.sokima.order.administration.java.domain.port.out.FindOrderOutPort;
import com.sokima.order.administration.java.domain.port.out.UpdateOrderOutPort;
import com.sokima.order.administration.java.domain.port.out.event.PublishSendableOutPort;
import com.sokima.order.administration.usecase.command.in.ReplaceShippingAddressCommand;
import com.sokima.order.administration.usecase.command.out.OrderInfoUpdatedEvent;
import com.sokima.order.administration.usecase.exception.UseCaseException;
import com.sokima.order.administration.usecase.port.ModifyShippingInPort;

public final class ModifyShippingAddressUseCase implements ModifyShippingInPort {

    private final FindOrderOutPort findOrderOutPort;
    private final UpdateOrderOutPort updateOrderOutPort;
    private final UpdateOrderOperation updateOrderOperation;
    private final PublishSendableOutPort publishSendableOutPort;

    public ModifyShippingAddressUseCase(final FindOrderOutPort findOrderOutPort, final UpdateOrderOutPort updateOrderOutPort, final UpdateOrderOperation updateOrderOperation, final PublishSendableOutPort publishSendableOutPort) {
        this.findOrderOutPort = findOrderOutPort;
        this.updateOrderOutPort = updateOrderOutPort;
        this.updateOrderOperation = updateOrderOperation;
        this.publishSendableOutPort = publishSendableOutPort;
    }

    @Override
    public void modifyShipping(final ReplaceShippingAddressCommand shippingAddressCommand) {
        findOrderOutPort.findOrderById(shippingAddressCommand.orderId())
                .filter(order -> updateOrderOperation.isAllowed(new OperationContext(order)))
                .map(order -> order.updateShippingAddress(shippingAddressCommand.shippingAddress()))
                .map(updateOrderOutPort::updateOrder)
                .map(order -> new OrderInfoUpdatedEvent(order.orderId()))
                .ifPresentOrElse(publishSendableOutPort::publish, () -> {
                    throw new UseCaseException("Order does not exist or modification is not allowed.");
                });
    }
}
