package com.sokima.order.administration.usecase.command.update;

import com.sokima.order.administration.java.domain.Order;
import com.sokima.order.administration.java.domain.PaymentData;
import com.sokima.order.administration.java.domain.Status;
import com.sokima.order.administration.java.domain.business.operation.UpdateOrderOperation;
import com.sokima.order.administration.java.domain.business.operation.in.OperationContext;
import com.sokima.order.administration.java.domain.port.out.FindOrderOutPort;
import com.sokima.order.administration.java.domain.port.out.UpdateOrderOutPort;
import com.sokima.order.administration.java.domain.port.out.event.PublishSendableOutPort;
import com.sokima.order.administration.usecase.command.in.ChangePaymentMethodCommand;
import com.sokima.order.administration.usecase.command.out.OrderInfoUpdatedEvent;
import com.sokima.order.administration.usecase.exception.UseCaseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ModifyPaymentMethodUseCaseTest {

    @InjectMocks ModifyPaymentMethodUseCase modifyPaymentMethodUseCase;

    @Mock
    FindOrderOutPort findOrderOutPort;

    @Mock
    UpdateOrderOutPort updateOrderOutPort;

    @Mock
    UpdateOrderOperation updateOrderOperation;

    @Mock
    PublishSendableOutPort publishSendableOutPort;

    @Captor
    ArgumentCaptor<Order> orderArgumentCaptor;

    @Test
    void modifyPaymentMethod_orderExistAndPaymentDataNot_updatedAndPublishedEvent() {
        var order = new Order(
            "ordr_id",
            "123",
                Status.CREATED,
                null,
                null,
                null
        );

        Mockito.when(findOrderOutPort.findOrderById("ordr_id")).thenReturn(Optional.of(order));
        Mockito.when(updateOrderOperation.isAllowed(any(OperationContext.class))).thenReturn(true);
        Mockito.when(updateOrderOutPort.updateOrder(any(Order.class))).thenAnswer(a -> a.getArgument(0));

        modifyPaymentMethodUseCase.modifyPaymentMethod(new ChangePaymentMethodCommand("ordr_id", "CASH"));

        Mockito.verify(updateOrderOutPort, Mockito.times(1)).updateOrder(orderArgumentCaptor.capture());
        Mockito.verify(publishSendableOutPort, Mockito.times(1)).publish(any(OrderInfoUpdatedEvent.class));

        var value = orderArgumentCaptor.getValue();
        Assertions.assertNotNull(value);
        Assertions.assertNotNull(value.paymentData());
        Assertions.assertEquals("CASH", value.paymentData().paymentMethod());
        Assertions.assertTrue(value.paymentData().validate());
    }

    @Test
    void modifyPaymentMethod_orderExist_updatedAndPublishedEvent() {
        var order = new Order(
                "ordr_id",
                "123",
                Status.CREATED,
                null,
                null,
                PaymentData.from("APPLE_PAY")
        );

        Mockito.when(findOrderOutPort.findOrderById("ordr_id")).thenReturn(Optional.of(order));
        Mockito.when(updateOrderOperation.isAllowed(any(OperationContext.class))).thenReturn(true);
        Mockito.when(updateOrderOutPort.updateOrder(any(Order.class))).thenAnswer(a -> a.getArgument(0));

        modifyPaymentMethodUseCase.modifyPaymentMethod(new ChangePaymentMethodCommand("ordr_id", "CASH"));

        Mockito.verify(updateOrderOutPort, Mockito.times(1)).updateOrder(orderArgumentCaptor.capture());
        Mockito.verify(publishSendableOutPort, Mockito.times(1)).publish(any(OrderInfoUpdatedEvent.class));

        var value = orderArgumentCaptor.getValue();
        Assertions.assertNotNull(value);
        Assertions.assertNotNull(value.paymentData());
        Assertions.assertNotEquals("APPLE_PAY", value.paymentData().paymentMethod());
        Assertions.assertEquals("CASH", value.paymentData().paymentMethod());
        Assertions.assertTrue(value.paymentData().validate());
    }

    @Test
    void modifyPaymentMethod_orderNotExist_useCaseException() {
        Mockito.when(findOrderOutPort.findOrderById("ordr_id")).thenReturn(Optional.empty());

        Assertions.assertThrows(UseCaseException.class, () -> modifyPaymentMethodUseCase.modifyPaymentMethod(new ChangePaymentMethodCommand("ordr_id", "CASH")));
    }

    @Test
    void modifyPaymentMethod_operationNotAllowed_useCaseException() {
        Mockito.when(findOrderOutPort.findOrderById("ordr_id")).thenReturn(Optional.of(new Order(
                null, null, null, null, null, null
        )));
        Mockito.when(updateOrderOperation.isAllowed(any(OperationContext.class))).thenReturn(false);

        Assertions.assertThrows(UseCaseException.class, () -> modifyPaymentMethodUseCase.modifyPaymentMethod(new ChangePaymentMethodCommand("ordr_id", "CASH")));
    }
}