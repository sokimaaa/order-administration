package com.sokima.order.administration.usecase.flow.confirm;

import com.sokima.order.administration.java.domain.*;
import com.sokima.order.administration.java.domain.business.operation.ChangeOrderStatusOperation;
import com.sokima.order.administration.java.domain.business.operation.in.OperationContext;
import com.sokima.order.administration.java.domain.port.out.FindOrderOutPort;
import com.sokima.order.administration.java.domain.port.out.UpdateOrderOutPort;
import com.sokima.order.administration.java.domain.port.out.event.PublishSendableOutPort;
import com.sokima.order.administration.usecase.flow.cancel.CancelOrderUseCase;
import com.sokima.order.administration.usecase.flow.exception.UseCaseException;
import com.sokima.order.administration.usecase.in.command.CancelOrderCommand;
import com.sokima.order.administration.usecase.in.command.ConfirmOrderCommand;
import com.sokima.order.administration.usecase.out.event.OrderCancelledEvent;
import com.sokima.order.administration.usecase.out.event.OrderConfirmedEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ConfirmOrderUseCaseTest {

    @InjectMocks ConfirmOrderUseCase confirmOrderUseCase;

    @Mock
    FindOrderOutPort findOrderOutPort;

    @Mock
    UpdateOrderOutPort updateOrderOutPort;

    @Mock
    PublishSendableOutPort publishSendableOutPort;

    @Mock
    ChangeOrderStatusOperation changeOrderStatusOperation;

    @Test
    void confirmOrder_orderExistsWithAllowedStatus_eventPublished() {
        var order = new Order(
                123, "123", Status.CREATED,
                Products.from(Set.of("1"), 1.f),
                new DeliveryData("test address"),
                PaymentData.from("CASH")
        );

        Mockito.when(findOrderOutPort.findOrderById(123)).thenReturn(Optional.of(order));
        Mockito.when(updateOrderOutPort.updateOrder(any(Order.class))).thenAnswer(a -> a.getArgument(0));
        Mockito.when(changeOrderStatusOperation.isAllowed(any(OperationContext.class))).thenReturn(true);

        confirmOrderUseCase.confirmOrder(new ConfirmOrderCommand(123));

        Mockito.verify(publishSendableOutPort, Mockito.times(1)).publish(any(OrderConfirmedEvent.class));
        Mockito.verify(findOrderOutPort, Mockito.times(1)).findOrderById(123);
        Mockito.verify(updateOrderOutPort, Mockito.times(1)).updateOrder(any(Order.class));
    }

    @Test
    void confirmOrder_orderDoesNotExist_useCaseException() {
        Mockito.when(findOrderOutPort.findOrderById(123)).thenReturn(Optional.empty());

        Assertions.assertThrows(UseCaseException.class, () -> confirmOrderUseCase.confirmOrder(new ConfirmOrderCommand(123)));
    }

    @Test
    void confirmOrder_invalidOrderFound_useCaseException() {
        var order = new Order(
                123, "123", null,
                null,
                null,
                null
        );

        Mockito.when(findOrderOutPort.findOrderById(123)).thenReturn(Optional.of(order));
        Assertions.assertThrows(UseCaseException.class, () -> confirmOrderUseCase.confirmOrder(new ConfirmOrderCommand(123)));
    }

    @Test
    void confirmOrder_operationIsNowAllowed_useCaseException() {
        var order = new Order(
                123, "123", Status.CREATED,
                Products.from(Set.of("1"), 1.f),
                new DeliveryData("test address"),
                PaymentData.from("CASH")
        );

        Mockito.when(findOrderOutPort.findOrderById(123)).thenReturn(Optional.of(order));
        Mockito.when(changeOrderStatusOperation.isAllowed(any(OperationContext.class))).thenReturn(false);
        Assertions.assertThrows(UseCaseException.class, () -> confirmOrderUseCase.confirmOrder(new ConfirmOrderCommand(123)));
    }
}