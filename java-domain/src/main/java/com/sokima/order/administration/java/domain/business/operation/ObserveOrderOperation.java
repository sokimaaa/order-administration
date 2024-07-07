package com.sokima.order.administration.java.domain.business.operation;

import com.sokima.order.administration.java.domain.business.operation.in.OperationContext;

import java.util.Objects;

public final class ObserveOrderOperation implements Operation {
    @Override
    public boolean isAllowed(final OperationContext operationContext) {
        final var targetAccountId = operationContext.targetAccountId();
        return Objects.nonNull(targetAccountId) &&
                operationContext.currentOrder().accountId().equals(targetAccountId);
    }
}
