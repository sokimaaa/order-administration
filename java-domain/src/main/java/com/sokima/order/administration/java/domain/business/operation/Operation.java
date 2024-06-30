package com.sokima.order.administration.java.domain.business.operation;

public interface Operation {
    boolean isAllowed(final OperationContext operationContext);
}
