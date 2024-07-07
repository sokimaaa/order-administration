package com.sokima.order.administration.usecase.command.status;

import com.sokima.order.administration.java.domain.Order;
import com.sokima.order.administration.java.domain.Status;
import com.sokima.order.administration.java.domain.business.operation.ChangeOrderStatusOperation;
import com.sokima.order.administration.java.domain.business.operation.in.OperationContext;
import com.sokima.order.administration.java.domain.port.out.FindOrderOutPort;
import com.sokima.order.administration.java.domain.port.out.UpdateOrderOutPort;
import com.sokima.order.administration.java.domain.port.out.event.PublishSendableOutPort;
import com.sokima.order.administration.usecase.exception.UseCaseException;
import com.sokima.order.administration.usecase.command.in.ConfirmOrderCommand;
import com.sokima.order.administration.usecase.command.out.OrderConfirmedEvent;
import com.sokima.order.administration.usecase.port.ConfirmOrderInPort;

public final class ConfirmOrderUseCase implements ConfirmOrderInPort {

    private final FindOrderOutPort findOrderOutPort;
    private final UpdateOrderOutPort updateOrderOutPort;
    private final PublishSendableOutPort publishSendableOutPort;
    private final ChangeOrderStatusOperation changeOrderStatusOperation;

    public ConfirmOrderUseCase(final FindOrderOutPort findOrderOutPort, final UpdateOrderOutPort updateOrderOutPort, final PublishSendableOutPort publishSendableOutPort, final ChangeOrderStatusOperation changeOrderStatusOperation) {
        this.findOrderOutPort = findOrderOutPort;
        this.updateOrderOutPort = updateOrderOutPort;
        this.publishSendableOutPort = publishSendableOutPort;
        this.changeOrderStatusOperation = changeOrderStatusOperation;
    }

    @Override
    public void confirmOrder(final ConfirmOrderCommand confirmOrderCommand) {
        findOrderOutPort.findOrderById(confirmOrderCommand.orderId())
                .filter(Order::validate)
                .filter(order -> changeOrderStatusOperation.isAllowed(
                        new OperationContext(order, Status.APPROVED)
                ))
                .map(order -> order.updateStatus(Status.APPROVED))
                .map(updateOrderOutPort::updateOrder)
                .map(order -> new OrderConfirmedEvent(order.orderId()))
                .ifPresentOrElse(publishSendableOutPort::publish, () -> {
                    throw new UseCaseException("Order was not found. Confirm Command is rejected.");
                });
    }
}
