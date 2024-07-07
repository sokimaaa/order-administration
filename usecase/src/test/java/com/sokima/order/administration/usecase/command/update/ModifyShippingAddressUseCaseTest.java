package com.sokima.order.administration.usecase.command.update;

import com.sokima.order.administration.java.domain.DeliveryData;
import com.sokima.order.administration.java.domain.Order;
import com.sokima.order.administration.java.domain.Status;
import com.sokima.order.administration.java.domain.business.operation.UpdateOrderOperation;
import com.sokima.order.administration.java.domain.business.operation.in.OperationContext;
import com.sokima.order.administration.java.domain.port.out.FindOrderOutPort;
import com.sokima.order.administration.java.domain.port.out.UpdateOrderOutPort;
import com.sokima.order.administration.java.domain.port.out.event.PublishSendableOutPort;
import com.sokima.order.administration.usecase.command.in.ReplaceShippingAddressCommand;
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
class ModifyShippingAddressUseCaseTest {

    @InjectMocks ModifyShippingAddressUseCase modifyShippingAddressUseCase;

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
    void modifyShipping_orderExistWithAnyShippingAddress_publishedEvent() {
        var order = new Order(
                "ordr_123",
                "123",
                Status.CREATED,
                null,
                new DeliveryData(null),
                null
        );

        Mockito.when(findOrderOutPort.findOrderById("ordr_123")).thenReturn(Optional.of(order));
        Mockito.when(updateOrderOutPort.updateOrder(any(Order.class))).thenAnswer(a -> a.getArgument(0));
        Mockito.when(updateOrderOperation.isAllowed(any(OperationContext.class))).thenReturn(true);

        modifyShippingAddressUseCase.modifyShipping(
                new ReplaceShippingAddressCommand("ordr_123", "new test address")
        );

        Mockito.verify(updateOrderOutPort, Mockito.times(1)).updateOrder(orderArgumentCaptor.capture());
        Mockito.verify(publishSendableOutPort, Mockito.times(1)).publish(any(OrderInfoUpdatedEvent.class));

        var orderValue = orderArgumentCaptor.getValue();
        Assertions.assertNotNull(orderValue);
        Assertions.assertEquals("ordr_123", orderValue.orderId());
        Assertions.assertNotNull(orderValue.deliveryData());
        Assertions.assertTrue(orderValue.deliveryData().validate());
    }

    @Test
    void modifyShipping_orderExistWithShippingAddress_publishedEvent() {
        var order = new Order(
                "ordr_123",
                "123",
                Status.CREATED,
                null,
                new DeliveryData("test address"),
                null
        );

        Mockito.when(findOrderOutPort.findOrderById("ordr_123")).thenReturn(Optional.of(order));
        Mockito.when(updateOrderOutPort.updateOrder(any(Order.class))).thenAnswer(a -> a.getArgument(0));
        Mockito.when(updateOrderOperation.isAllowed(any(OperationContext.class))).thenReturn(true);

        modifyShippingAddressUseCase.modifyShipping(
                new ReplaceShippingAddressCommand("ordr_123", "new test address")
        );

        Mockito.verify(updateOrderOutPort, Mockito.times(1)).updateOrder(orderArgumentCaptor.capture());
        Mockito.verify(publishSendableOutPort, Mockito.times(1)).publish(any(OrderInfoUpdatedEvent.class));

        var orderValue = orderArgumentCaptor.getValue();
        Assertions.assertNotNull(orderValue);
        Assertions.assertEquals("ordr_123", orderValue.orderId());
        Assertions.assertNotNull(orderValue.deliveryData());
        Assertions.assertTrue(orderValue.deliveryData().validate());
        Assertions.assertEquals("new test address", orderValue.deliveryData().shippingAddress());
    }

    @Test
    void modifyShipping_orderNotExist_useCaseException() {
        Mockito.when(findOrderOutPort.findOrderById("ordr_123")).thenReturn(Optional.empty());
        Assertions.assertThrows(UseCaseException.class, () -> modifyShippingAddressUseCase.modifyShipping(
                new ReplaceShippingAddressCommand("ordr_123", "new test address")
        ));
    }

    @Test
    void modifyShipping_orderExistAndNotAllowed_useCaseException() {
        var order = new Order(
                "ordr_123",
                null,
                Status.CREATED,
                null,
                null,
                null
        );

        Mockito.when(findOrderOutPort.findOrderById("ordr_123")).thenReturn(Optional.of(order));
        Mockito.when(updateOrderOperation.isAllowed(any(OperationContext.class))).thenReturn(false);

        Assertions.assertThrows(UseCaseException.class, () -> modifyShippingAddressUseCase.modifyShipping(
                new ReplaceShippingAddressCommand("ordr_123", "new test address")
        ));
    }
}