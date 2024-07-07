package com.sokima.order.administration.usecase.port;

import com.sokima.order.administration.usecase.query.in.TrackingQuery;
import com.sokima.order.administration.usecase.query.out.TrackedOrderResponse;

public interface TrackingOrderInPort {
    TrackedOrderResponse trackOrder(final TrackingQuery trackingQuery);
}
