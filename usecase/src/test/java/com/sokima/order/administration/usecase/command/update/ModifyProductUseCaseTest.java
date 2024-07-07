package com.sokima.order.administration.usecase.command.update;

import com.sokima.order.administration.java.domain.Order;
import com.sokima.order.administration.java.domain.Products;
import com.sokima.order.administration.java.domain.Status;
import com.sokima.order.administration.java.domain.business.operation.UpdateOrderOperation;
import com.sokima.order.administration.java.domain.business.operation.in.OperationContext;
import com.sokima.order.administration.java.domain.port.out.FindOrderOutPort;
import com.sokima.order.administration.java.domain.port.out.UpdateOrderOutPort;
import com.sokima.order.administration.java.domain.port.out.event.PublishSendableOutPort;
import com.sokima.order.administration.usecase.command.in.UpdateProductCommand;
import com.sokima.order.administration.usecase.command.out.OrderInfoUpdatedEvent;
import com.sokima.order.administration.usecase.exception.UseCaseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ModifyProductUseCaseTest {

    @InjectMocks
    ModifyProductUseCase modifyProductUseCase;

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
    void modifyProduct_orderAndProductsExist_updatedAndPublished() {
        var order = new Order(
                "ordr_id",
                "123",
                Status.CREATED,
                Products.from(Set.of("1", "2"), 1.f),
                null,
                null
        );

        Mockito.when(findOrderOutPort.findOrderById("ordr_id")).thenReturn(Optional.of(order));
        Mockito.when(updateOrderOperation.isAllowed(any(OperationContext.class))).thenReturn(true);
        Mockito.when(updateOrderOutPort.updateOrder(any(Order.class))).thenAnswer(a -> a.getArgument(0));

        modifyProductUseCase.modifyProduct(new UpdateProductCommand("ordr_id", Set.of("1"), 0.5f, null));

        Mockito.verify(updateOrderOutPort, times(1)).updateOrder(orderArgumentCaptor.capture());
        Mockito.verify(publishSendableOutPort, times(1)).publish(any(OrderInfoUpdatedEvent.class));

        var value = orderArgumentCaptor.getValue();
        Assertions.assertNotNull(value);
        Assertions.assertNotNull(value.products());
        Assertions.assertFalse(value.products().getProductIds().contains("1"));
        Assertions.assertTrue(value.products().getProductIds().contains("2"));
        Assertions.assertEquals(0.5f, value.products().getAmount());
        Assertions.assertTrue(value.products().validate());
    }

    @Test
    void modifyProduct_orderExistAndProductsNotExist_updatedAndPublished() {
        var order = new Order(
                "ordr_id",
                "123",
                Status.CREATED,
                Products.from(null, null),
                null,
                null
        );

        Mockito.when(findOrderOutPort.findOrderById("ordr_id")).thenReturn(Optional.of(order));
        Mockito.when(updateOrderOperation.isAllowed(any(OperationContext.class))).thenReturn(true);
        Mockito.when(updateOrderOutPort.updateOrder(any(Order.class))).thenAnswer(a -> a.getArgument(0));

        modifyProductUseCase.modifyProduct(new UpdateProductCommand("ordr_id", Set.of("1"), -0.5f, null));

        Mockito.verify(updateOrderOutPort, times(1)).updateOrder(orderArgumentCaptor.capture());
        Mockito.verify(publishSendableOutPort, times(1)).publish(any(OrderInfoUpdatedEvent.class));

        var value = orderArgumentCaptor.getValue();
        Assertions.assertNotNull(value);
        Assertions.assertNotNull(value.products());
        Assertions.assertTrue(value.products().getProductIds().contains("1"));
        Assertions.assertEquals(0.5f, value.products().getAmount());
        Assertions.assertTrue(value.products().validate());
    }

    @Test
    void modifyProduct_orderNotExist_useCaseException() {
        Mockito.when(findOrderOutPort.findOrderById("ordr_id")).thenReturn(Optional.empty());
        Assertions.assertThrows(UseCaseException.class, () -> modifyProductUseCase.modifyProduct(new UpdateProductCommand("ordr_id", Set.of("1"), 0.5f, null)));
    }

    @Test
    void modifyProduct_operationIsNotAllowed_useCaseException() {
        Mockito.when(findOrderOutPort.findOrderById("ordr_id")).thenReturn(Optional.of(new Order(null, null, null, null, null, null)));
        Mockito.when(updateOrderOperation.isAllowed(any(OperationContext.class))).thenReturn(false);

        Assertions.assertThrows(UseCaseException.class, () -> modifyProductUseCase.modifyProduct(new UpdateProductCommand("ordr_id", Set.of("1"), 0.5f, null)));
    }
}