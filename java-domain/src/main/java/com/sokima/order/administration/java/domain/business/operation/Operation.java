package com.sokima.order.administration.java.domain.business.operation;

import com.sokima.order.administration.java.domain.business.operation.in.OperationContext;

public interface Operation {
    boolean isAllowed(final OperationContext operationContext);
}
