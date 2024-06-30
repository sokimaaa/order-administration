package com.sokima.order.administration.java.domain.business.operation;

import com.sokima.order.administration.java.domain.Status;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateOrderOperationTest {

    @InjectMocks UpdateOrderOperation updateOrderOperation;

    @Test
    void isAllowed_currentStatusIsNotAllowed_false() {
        var operationContext = new OperationContext(Status.IN_PROGRESS);

        Assertions.assertFalse(updateOrderOperation.isAllowed(operationContext));
    }

    @Test
    void isAllowed_currentStatusIsAllowed_true() {
        var operationContext = new OperationContext(Status.CREATED);

        Assertions.assertTrue(updateOrderOperation.isAllowed(operationContext));
    }

}