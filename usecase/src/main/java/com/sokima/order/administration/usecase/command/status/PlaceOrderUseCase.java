package com.sokima.order.administration.usecase.command.status;

import com.sokima.order.administration.java.domain.*;
import com.sokima.order.administration.java.domain.business.id.GenerateOrderId;
import com.sokima.order.administration.java.domain.port.out.SaveOrderOutPort;
import com.sokima.order.administration.java.domain.port.out.event.PublishSendableOutPort;
import com.sokima.order.administration.usecase.command.in.command.PlaceOrderCommand;
import com.sokima.order.administration.usecase.command.out.event.OrderCreatedEvent;
import com.sokima.order.administration.usecase.exception.UseCaseException;
import com.sokima.order.administration.usecase.port.PlaceOrderInPort;

import java.util.Objects;

public final class PlaceOrderUseCase implements PlaceOrderInPort {

    private final SaveOrderOutPort saveOrderOutPort;
    private final PublishSendableOutPort publishSendableOutPort;

    public PlaceOrderUseCase(final SaveOrderOutPort saveOrderOutPort, final PublishSendableOutPort publishSendableOutPort) {
        this.saveOrderOutPort = saveOrderOutPort;
        this.publishSendableOutPort = publishSendableOutPort;
    }

    @Override
    public void placeOrder(final PlaceOrderCommand placeOrderCommand) {
        try {
            final var order = new Order(
                    GenerateOrderId.generateOrderId(),
                    placeOrderCommand.accountId(),
                    Status.CREATED,
                    buildProducts(placeOrderCommand),
                    buildDeliveryData(placeOrderCommand),
                    buildPaymentData(placeOrderCommand)
            );
            final var savedOrder = saveOrderOutPort.saveOrder(order);
            publishSendableOutPort.publish(new OrderCreatedEvent(savedOrder.orderId()));
        } catch (Exception e) {
            throw new UseCaseException(e.getMessage());
        }
    }

    private Products buildProducts(final PlaceOrderCommand placeOrderCommand) {
        return Products.from(placeOrderCommand.productIds(), placeOrderCommand.amount());
    }

    private PaymentData buildPaymentData(final PlaceOrderCommand placeOrderCommand) {
        return Objects.nonNull(placeOrderCommand.paymentMethod()) ? PaymentData.from(placeOrderCommand.paymentMethod())
                : null;
    }

    private DeliveryData buildDeliveryData(final PlaceOrderCommand placeOrderCommand) {
        return new DeliveryData(placeOrderCommand.shippingAddress(), placeOrderCommand.productProfile());
    }
}
