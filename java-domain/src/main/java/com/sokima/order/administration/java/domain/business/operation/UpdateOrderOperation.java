package com.sokima.order.administration.java.domain.business.operation;

import com.sokima.order.administration.java.domain.Status;
import com.sokima.order.administration.java.domain.business.operation.in.OperationContext;

import java.util.Set;

public final class UpdateOrderOperation implements Operation {

    private static final Set<Status> ALLOWED_STATUSES = Set.of(Status.CREATED);

    @Override
    public boolean isAllowed(final OperationContext operationContext) {
        return ALLOWED_STATUSES.contains(operationContext.currentStatus());
    }
}
