package com.sokima.order.administration.java.domain.business.operation;

import com.sokima.order.administration.java.domain.Status;
import com.sokima.order.administration.java.domain.business.operation.in.OperationContext;

import java.util.Objects;
import java.util.Set;

public final class ChangeOrderStatusOperation implements Operation {

    private static final Set<Status> NOT_ALLOWED_STATUSES = Set.of(Status.CANCELLED, Status.DONE);

    @Override
    public boolean isAllowed(final OperationContext operationContext) {
        final var currentStatus = operationContext.currentOrder().status();
        final var targetStatus = operationContext.targetStatus();
        if (Objects.isNull(targetStatus)) {
            return !NOT_ALLOWED_STATUSES.contains(currentStatus);
        }
        return !NOT_ALLOWED_STATUSES.contains(currentStatus) &&
                currentStatus.step() < targetStatus.step();
    }
}
