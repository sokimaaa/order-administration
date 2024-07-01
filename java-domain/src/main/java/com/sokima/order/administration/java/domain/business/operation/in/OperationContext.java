package com.sokima.order.administration.java.domain.business.operation.in;

import com.sokima.order.administration.java.domain.Status;

public record OperationContext(
        Status currentStatus,
        Status targetStatus
) {
    public OperationContext(final Status currentStatus) {
        this(currentStatus, null);
    }
}
