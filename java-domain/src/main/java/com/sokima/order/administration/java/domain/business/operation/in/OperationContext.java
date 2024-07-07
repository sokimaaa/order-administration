package com.sokima.order.administration.java.domain.business.operation.in;

import com.sokima.order.administration.java.domain.Order;
import com.sokima.order.administration.java.domain.Status;

public record OperationContext(
        Order currentOrder,
        Status targetStatus,
        String targetAccountId
) {
    public OperationContext(final Order currentOrder) {
        this(currentOrder, null, null);
    }

    public OperationContext(final Order currentOrder, final Status targetStatus) {
        this(currentOrder, targetStatus, null);
    }

    public OperationContext(final Order currentOrder, final String targetAccountId) {
        this(currentOrder, null, targetAccountId);
    }
}
