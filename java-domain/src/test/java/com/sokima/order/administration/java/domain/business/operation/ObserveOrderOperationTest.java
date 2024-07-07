package com.sokima.order.administration.java.domain.business.operation;

import com.sokima.order.administration.java.domain.Order;
import com.sokima.order.administration.java.domain.business.operation.in.OperationContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ObserveOrderOperationTest {

    @InjectMocks ObserveOrderOperation observeOrderOperation;

    @Test
    void isAllowed_targetAccountWrong_false() {
        var order = new Order(
                "ordr_id",
                "acct_id",
                null, null, null, null
        );
        Assertions.assertFalse(observeOrderOperation.isAllowed(new OperationContext(order, "acct_id_2")));
    }

    @Test
    void isAllowed_targetAccountNull_false() {
        var order = new Order(
                "ordr_id",
                "acct_id",
                null, null, null, null
        );
        Assertions.assertFalse(observeOrderOperation.isAllowed(new OperationContext(order, (String) null)));
    }

    @Test
    void isAllowed_targetAccountMatch_true() {
        var order = new Order(
                "ordr_id",
                "acct_id",
                null, null, null, null
        );
        Assertions.assertTrue(observeOrderOperation.isAllowed(new OperationContext(order, "acct_id")));
    }
}