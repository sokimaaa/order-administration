package com.sokima.order.administration.java.domain.business.operation;

import com.sokima.order.administration.java.domain.Order;
import com.sokima.order.administration.java.domain.Status;
import com.sokima.order.administration.java.domain.business.operation.in.OperationContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ChangeOrderStatusOperationTest {

    @InjectMocks ChangeOrderStatusOperation changeOrderStatusOperation;

    @Test
    void isAllowed_targetStatusIsNull_false() {
        var order = new Order(
                null, null,
                Status.CANCELLED,
                null, null, null
        );
        var operationContext = new OperationContext(order);
        Assertions.assertFalse(changeOrderStatusOperation.isAllowed(operationContext));
    }

    @Test
    void isAllowed_targetStatusIsNull_true() {
        var order = new Order(
                null, null,
                Status.APPROVED,
                null, null, null
        );
        var operationContext = new OperationContext(order);
        Assertions.assertTrue(changeOrderStatusOperation.isAllowed(operationContext));
    }

    @Test
    void isAllowed_targetStatusIsValid_true() {
        var order = new Order(
                null, null,
                Status.APPROVED,
                null, null, null
        );
        var operationContext = new OperationContext(order, Status.IN_PROGRESS);
        Assertions.assertTrue(changeOrderStatusOperation.isAllowed(operationContext));
    }

    @Test
    void isAllowed_targetStatusIsValid_false() {
        var order = new Order(
                null, null,
                Status.IN_PROGRESS,
                null, null, null
        );
        var operationContext = new OperationContext(order, Status.APPROVED);
        Assertions.assertFalse(changeOrderStatusOperation.isAllowed(operationContext));
    }

    @Test
    void isAllowed_targetStatusIsValidAndChangeForbiddenStatus_false() {
        var order = new Order(
                null, null,
                Status.CANCELLED,
                null, null, null
        );
        var operationContext = new OperationContext(order, Status.DONE);
        Assertions.assertFalse(changeOrderStatusOperation.isAllowed(operationContext));
    }
}