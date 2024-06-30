package com.sokima.order.administration.java.domain.business.operation;

import com.sokima.order.administration.java.domain.Status;
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
        var operationContext = new OperationContext(Status.CANCELLED);
        Assertions.assertFalse(changeOrderStatusOperation.isAllowed(operationContext));
    }

    @Test
    void isAllowed_targetStatusIsNull_true() {
        var operationContext = new OperationContext(Status.APPROVED);
        Assertions.assertTrue(changeOrderStatusOperation.isAllowed(operationContext));
    }

    @Test
    void isAllowed_targetStatusIsValid_true() {
        var operationContext = new OperationContext(Status.APPROVED, Status.IN_PROGRESS);
        Assertions.assertTrue(changeOrderStatusOperation.isAllowed(operationContext));
    }

    @Test
    void isAllowed_targetStatusIsValid_false() {
        var operationContext = new OperationContext(Status.IN_PROGRESS, Status.APPROVED);
        Assertions.assertFalse(changeOrderStatusOperation.isAllowed(operationContext));
    }

    @Test
    void isAllowed_targetStatusIsValidAndChangeForbiddenStatus_false() {
        var operationContext = new OperationContext(Status.CANCELLED, Status.DONE);
        Assertions.assertFalse(changeOrderStatusOperation.isAllowed(operationContext));
    }
}