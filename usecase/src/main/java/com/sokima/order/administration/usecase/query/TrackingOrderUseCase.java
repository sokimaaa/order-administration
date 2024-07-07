package com.sokima.order.administration.usecase.query;

import com.sokima.order.administration.java.domain.business.operation.ObserveOrderOperation;
import com.sokima.order.administration.java.domain.business.operation.in.OperationContext;
import com.sokima.order.administration.java.domain.port.out.FindOrderOutPort;
import com.sokima.order.administration.usecase.exception.UseCaseException;
import com.sokima.order.administration.usecase.port.TrackingOrderInPort;
import com.sokima.order.administration.usecase.query.in.TrackingQuery;
import com.sokima.order.administration.usecase.query.out.TrackedOrderResponse;

public final class TrackingOrderUseCase implements TrackingOrderInPort {

    private final ObserveOrderOperation observeOrderOperation;
    private final FindOrderOutPort findOrderOutPort;

    public TrackingOrderUseCase(final ObserveOrderOperation observeOrderOperation, final FindOrderOutPort findOrderOutPort) {
        this.observeOrderOperation = observeOrderOperation;
        this.findOrderOutPort = findOrderOutPort;
    }

    @Override
    public TrackedOrderResponse trackOrder(final TrackingQuery trackingQuery) {
        return findOrderOutPort.findOrderById(trackingQuery.orderId())
                .filter(order -> observeOrderOperation.isAllowed(new OperationContext(order, trackingQuery.accountId())))
                .map(order -> new TrackedOrderResponse(order, order.status().description()))
                .orElseThrow(() -> new UseCaseException("Order not found or not eligible to track it."));
    }
}
