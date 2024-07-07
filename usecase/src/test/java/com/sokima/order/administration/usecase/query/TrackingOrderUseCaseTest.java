package com.sokima.order.administration.usecase.query;

import com.sokima.order.administration.java.domain.Order;
import com.sokima.order.administration.java.domain.Status;
import com.sokima.order.administration.java.domain.business.operation.ObserveOrderOperation;
import com.sokima.order.administration.java.domain.business.operation.in.OperationContext;
import com.sokima.order.administration.java.domain.port.out.FindOrderOutPort;
import com.sokima.order.administration.usecase.exception.UseCaseException;
import com.sokima.order.administration.usecase.query.in.TrackingQuery;
import com.sokima.order.administration.usecase.query.out.TrackedOrderResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class TrackingOrderUseCaseTest {

    @InjectMocks TrackingOrderUseCase trackingOrderUseCase;

    @Mock
    FindOrderOutPort findOrderOutPort;

    @Mock
    ObserveOrderOperation observeOrderOperation;

    @Test
    void trackOrder_orderNotFound_useCaseException() {
        Mockito.when(findOrderOutPort.findOrderById("ordr_id")).thenReturn(Optional.empty());
        Assertions.assertThrows(UseCaseException.class, () -> trackingOrderUseCase.trackOrder(new TrackingQuery("ordr_id", null)));
    }

    @Test
    void trackOrder_notEligibleToTrack_useCaseException() {
        Mockito.when(findOrderOutPort.findOrderById("ordr_id")).thenReturn(Optional.of(
                new Order("ordr_id", "acct_id", null, null, null, null)
        ));
        Mockito.when(observeOrderOperation.isAllowed(any(OperationContext.class))).thenReturn(false);
        Assertions.assertThrows(UseCaseException.class, () -> trackingOrderUseCase.trackOrder(new TrackingQuery("ordr_id", "acct_id_2")));
    }

    @Test
    void trackOrder_orderFoundAndEligible_trackedResponse() {
        Mockito.when(findOrderOutPort.findOrderById("ordr_id")).thenReturn(Optional.of(
                new Order("ordr_id", "acct_id", Status.IN_PROGRESS, null, null, null)
        ));
        Mockito.when(observeOrderOperation.isAllowed(any(OperationContext.class))).thenReturn(true);

        var trackedOrderResponse = trackingOrderUseCase.trackOrder(new TrackingQuery("ordr_id", "acct_id"));

        Assertions.assertNotNull(trackedOrderResponse);
        Assertions.assertNotNull(trackedOrderResponse.order());
        Assertions.assertEquals(Status.IN_PROGRESS.description(), trackedOrderResponse.description());
    }
}