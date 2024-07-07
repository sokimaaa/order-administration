package com.sokima.order.administration.usecase.command.update;

import com.sokima.order.administration.java.domain.DeltaProducts;
import com.sokima.order.administration.java.domain.business.operation.UpdateOrderOperation;
import com.sokima.order.administration.java.domain.business.operation.in.OperationContext;
import com.sokima.order.administration.java.domain.port.out.FindOrderOutPort;
import com.sokima.order.administration.java.domain.port.out.UpdateOrderOutPort;
import com.sokima.order.administration.java.domain.port.out.event.PublishSendableOutPort;
import com.sokima.order.administration.usecase.command.in.UpdateProductCommand;
import com.sokima.order.administration.usecase.command.out.OrderInfoUpdatedEvent;
import com.sokima.order.administration.usecase.exception.UseCaseException;
import com.sokima.order.administration.usecase.port.ModifyProductInPort;

public final class ModifyProductUseCase implements ModifyProductInPort {

    private final FindOrderOutPort findOrderOutPort;
    private final UpdateOrderOperation updateOrderOperation;
    private final UpdateOrderOutPort updateOrderOutPort;
    private final PublishSendableOutPort publishSendableOutPort;

    public ModifyProductUseCase(final FindOrderOutPort findOrderOutPort, final UpdateOrderOperation updateOrderOperation, final UpdateOrderOutPort updateOrderOutPort, final PublishSendableOutPort publishSendableOutPort) {
        this.findOrderOutPort = findOrderOutPort;
        this.updateOrderOperation = updateOrderOperation;
        this.updateOrderOutPort = updateOrderOutPort;
        this.publishSendableOutPort = publishSendableOutPort;
    }

    @Override
    public void modifyProduct(final UpdateProductCommand updateProductCommand) {
        final var deltaProducts = DeltaProducts.from(updateProductCommand.deltaProductIds(), updateProductCommand.deltaAmount());
        findOrderOutPort.findOrderById(updateProductCommand.orderId())
                .filter(order -> updateOrderOperation.isAllowed(new OperationContext(order)))
                .map(order -> order.applyDeltaProducts(deltaProducts))
                .map(updateOrderOutPort::updateOrder)
                .map(order -> new OrderInfoUpdatedEvent(order.orderId()))
                .ifPresentOrElse(publishSendableOutPort::publish, () -> {
                    throw new UseCaseException("Order does not exist or modification is not allowed.");
                });
    }
}
