package com.sokima.order.administration.usecase.flow.cancel;

import com.sokima.order.administration.java.domain.Status;
import com.sokima.order.administration.java.domain.business.operation.ChangeOrderStatusOperation;
import com.sokima.order.administration.java.domain.business.operation.in.OperationContext;
import com.sokima.order.administration.java.domain.port.out.FindOrderOutPort;
import com.sokima.order.administration.java.domain.port.out.UpdateOrderOutPort;
import com.sokima.order.administration.java.domain.port.out.event.PublishSendableOutPort;
import com.sokima.order.administration.usecase.flow.exception.UseCaseException;
import com.sokima.order.administration.usecase.in.command.CancelOrderCommand;
import com.sokima.order.administration.usecase.out.event.OrderCancelledEvent;
import com.sokima.order.administration.usecase.port.CancelOrderInPort;

public final class CancelOrderUseCase implements CancelOrderInPort {

    private final FindOrderOutPort findOrderOutPort;
    private final UpdateOrderOutPort updateOrderOutPort;
    private final PublishSendableOutPort publishSendableOutPort;
    private final ChangeOrderStatusOperation changeOrderStatusOperation;

    public CancelOrderUseCase(final FindOrderOutPort findOrderOutPort, final UpdateOrderOutPort updateOrderOutPort, final PublishSendableOutPort publishSendableOutPort, final ChangeOrderStatusOperation changeOrderStatusOperation) {
        this.findOrderOutPort = findOrderOutPort;
        this.updateOrderOutPort = updateOrderOutPort;
        this.publishSendableOutPort = publishSendableOutPort;
        this.changeOrderStatusOperation = changeOrderStatusOperation;
    }

    @Override
    public void cancelOrder(final CancelOrderCommand cancelOrderCommand) {
        findOrderOutPort.findOrderById(cancelOrderCommand.orderId())
                .filter(order -> changeOrderStatusOperation.isAllowed(
                        new OperationContext(order.status(), Status.CANCELLED)
                ))
                .map(order -> order.updateStatus(Status.CANCELLED))
                .map(updateOrderOutPort::updateOrder)
                .map(order -> new OrderCancelledEvent(order.orderId()))
                .ifPresentOrElse(publishSendableOutPort::publish, () -> {
                    throw new UseCaseException("Order was not found. Cancelling rejected.");
                });
    }
}
