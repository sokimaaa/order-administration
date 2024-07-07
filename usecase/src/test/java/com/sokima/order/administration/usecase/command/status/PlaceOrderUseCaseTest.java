package com.sokima.order.administration.usecase.command.status;

import com.sokima.order.administration.java.domain.Order;
import com.sokima.order.administration.java.domain.Status;
import com.sokima.order.administration.java.domain.port.out.SaveOrderOutPort;
import com.sokima.order.administration.java.domain.port.out.event.PublishSendableOutPort;
import com.sokima.order.administration.usecase.command.in.PlaceOrderCommand;
import com.sokima.order.administration.usecase.command.out.OrderCreatedEvent;
import com.sokima.order.administration.usecase.exception.UseCaseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class PlaceOrderUseCaseTest {

    @InjectMocks PlaceOrderUseCase placeOrderUseCase;

    @Mock
    SaveOrderOutPort saveOrderOutPort;

    @Mock
    PublishSendableOutPort publishSendableOutPort;

    @Captor
    ArgumentCaptor<Order> orderArgumentCaptor;

    @Test
    void placeOrder_fullOrderInfo_eventPublished() {
        Mockito.when(saveOrderOutPort.saveOrder(any(Order.class))).thenAnswer(a -> a.getArgument(0));
        placeOrderUseCase.placeOrder(new PlaceOrderCommand(
                "123",
                Set.of("1", "2"),
                "test address",
                "20x20",
                1.f,
                "CASH"
        ));

        Mockito.verify(saveOrderOutPort, Mockito.times(1)).saveOrder(orderArgumentCaptor.capture());
        Mockito.verify(publishSendableOutPort, Mockito.times(1)).publish(any(OrderCreatedEvent.class));

        var order = orderArgumentCaptor.getValue();
        Assertions.assertNotNull(order);
        Assertions.assertTrue(order.orderId().startsWith("ordr_"));
        Assertions.assertEquals("123", order.accountId());
        Assertions.assertEquals(Status.CREATED, order.status());

        Assertions.assertNotNull(order.deliveryData());
        Assertions.assertTrue(order.deliveryData().validate());

        Assertions.assertNotNull(order.paymentData());
        Assertions.assertTrue(order.paymentData().validate());

        Assertions.assertNotNull(order.products());
        Assertions.assertTrue(order.products().validate());
    }

    @Test
    void placeOrder_shortenOrderInfo_eventPublished() {
        Mockito.when(saveOrderOutPort.saveOrder(any(Order.class))).thenAnswer(a -> a.getArgument(0));
        placeOrderUseCase.placeOrder(new PlaceOrderCommand(
                "123"
        ));

        Mockito.verify(saveOrderOutPort, Mockito.times(1)).saveOrder(orderArgumentCaptor.capture());
        Mockito.verify(publishSendableOutPort, Mockito.times(1)).publish(any(OrderCreatedEvent.class));

        var order = orderArgumentCaptor.getValue();
        Assertions.assertNotNull(order);
        Assertions.assertTrue(order.orderId().startsWith("ordr_"));
        Assertions.assertEquals("123", order.accountId());
        Assertions.assertEquals(Status.CREATED, order.status());
        Assertions.assertNotNull(order.deliveryData());
        Assertions.assertNotNull(order.paymentData());
        Assertions.assertNotNull(order.products());
    }

    @Test
    void placeOrder_notEnoughInfo_useCaseException() {
        Assertions.assertThrows(UseCaseException.class, () -> placeOrderUseCase.placeOrder(
                new PlaceOrderCommand(
                        null
                )
        ));
    }
}