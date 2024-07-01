package com.sokima.order.administration.usecase.flow.cancel;

import com.sokima.order.administration.java.domain.Order;
import com.sokima.order.administration.java.domain.Status;
import com.sokima.order.administration.java.domain.business.operation.ChangeOrderStatusOperation;
import com.sokima.order.administration.java.domain.business.operation.in.OperationContext;
import com.sokima.order.administration.java.domain.port.out.FindOrderOutPort;
import com.sokima.order.administration.java.domain.port.out.UpdateOrderOutPort;
import com.sokima.order.administration.java.domain.port.out.event.PublishSendableOutPort;
import com.sokima.order.administration.usecase.flow.exception.UseCaseException;
import com.sokima.order.administration.usecase.in.command.CancelOrderCommand;
import com.sokima.order.administration.usecase.out.event.OrderCancelledEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CancelOrderUseCaseTest {

    @InjectMocks
    CancelOrderUseCase cancelOrderUseCase;

    @Mock
    FindOrderOutPort findOrderOutPort;

    @Mock
    UpdateOrderOutPort updateOrderOutPort;

    @Mock
    PublishSendableOutPort publishSendableOutPort;

    @Mock
    ChangeOrderStatusOperation changeOrderStatusOperation;

    @Test
    void cancelOrder_orderExists_publishedEvent() {
        var order = new Order(
                123, null, Status.APPROVED, null, null, null
        );

        Mockito.when(findOrderOutPort.findOrderById(123)).thenReturn(Optional.of(order));
        Mockito.when(updateOrderOutPort.updateOrder(any(Order.class))).thenAnswer(a -> a.getArgument(0));
        Mockito.when(changeOrderStatusOperation.isAllowed(any(OperationContext.class))).thenReturn(true);

        cancelOrderUseCase.cancelOrder(new CancelOrderCommand(123, "test"));

        Mockito.verify(publishSendableOutPort, Mockito.times(1)).publish(any(OrderCancelledEvent.class));
        Mockito.verify(findOrderOutPort, Mockito.times(1)).findOrderById(123);
        Mockito.verify(updateOrderOutPort, Mockito.times(1)).updateOrder(any(Order.class));
    }

    @Test
    void cancelOrder_orderDoesNotExist_useCaseException() {
        Mockito.when(findOrderOutPort.findOrderById(123)).thenReturn(Optional.empty());

        Assertions.assertThrows(UseCaseException.class, () -> cancelOrderUseCase.cancelOrder(new CancelOrderCommand(123, "test")));
    }

    @Test
    void cancelOrder_operationNotAllowed_useCaseException() {
        var order = new Order(
                123, null, Status.CANCELLED, null, null, null
        );

        Mockito.when(findOrderOutPort.findOrderById(123)).thenReturn(Optional.of(order));
        Mockito.when(changeOrderStatusOperation.isAllowed(any(OperationContext.class))).thenReturn(false);

        Assertions.assertThrows(UseCaseException.class, () -> cancelOrderUseCase.cancelOrder(new CancelOrderCommand(123, "test")));

        Mockito.verify(findOrderOutPort, Mockito.times(1)).findOrderById(123);
    }
}