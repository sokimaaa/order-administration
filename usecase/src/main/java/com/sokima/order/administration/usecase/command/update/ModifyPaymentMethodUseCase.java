package com.sokima.order.administration.usecase.command.update;

import com.sokima.order.administration.java.domain.PaymentData;
import com.sokima.order.administration.java.domain.business.operation.UpdateOrderOperation;
import com.sokima.order.administration.java.domain.business.operation.in.OperationContext;
import com.sokima.order.administration.java.domain.port.out.FindOrderOutPort;
import com.sokima.order.administration.java.domain.port.out.UpdateOrderOutPort;
import com.sokima.order.administration.java.domain.port.out.event.PublishSendableOutPort;
import com.sokima.order.administration.usecase.command.in.ChangePaymentMethodCommand;
import com.sokima.order.administration.usecase.command.out.OrderInfoUpdatedEvent;
import com.sokima.order.administration.usecase.exception.UseCaseException;
import com.sokima.order.administration.usecase.port.ModifyPaymentMethodInPort;

public final class ModifyPaymentMethodUseCase implements ModifyPaymentMethodInPort {

    private final FindOrderOutPort findOrderOutPort;
    private final UpdateOrderOutPort updateOrderOutPort;
    private final UpdateOrderOperation updateOrderOperation;
    private final PublishSendableOutPort publishSendableOutPort;

    public ModifyPaymentMethodUseCase(final FindOrderOutPort findOrderOutPort, final UpdateOrderOutPort updateOrderOutPort, final UpdateOrderOperation updateOrderOperation, final PublishSendableOutPort publishSendableOutPort) {
        this.findOrderOutPort = findOrderOutPort;
        this.updateOrderOutPort = updateOrderOutPort;
        this.updateOrderOperation = updateOrderOperation;
        this.publishSendableOutPort = publishSendableOutPort;
    }

    @Override
    public void modifyPaymentMethod(final ChangePaymentMethodCommand changePaymentMethodCommand) {
        findOrderOutPort.findOrderById(changePaymentMethodCommand.orderId())
                .filter(order -> updateOrderOperation.isAllowed(new OperationContext(order.status())))
                .map(order -> order.updatePaymentData(PaymentData.from(changePaymentMethodCommand.paymentMethod())))
                .map(updateOrderOutPort::updateOrder)
                .map(order -> new OrderInfoUpdatedEvent(order.orderId()))
                .ifPresentOrElse(publishSendableOutPort::publish, () -> {
                    throw new UseCaseException("Order does not exist or modification is not allowed.");
                });
    }
}
