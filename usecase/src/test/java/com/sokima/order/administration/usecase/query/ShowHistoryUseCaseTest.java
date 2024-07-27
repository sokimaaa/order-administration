package com.sokima.order.administration.usecase.query;

import com.sokima.order.administration.java.domain.Order;
import com.sokima.order.administration.java.domain.port.out.FindOrderOutPort;
import com.sokima.order.administration.usecase.query.in.HistoryQuery;
import com.sokima.order.administration.usecase.query.out.OrderHistoryResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Set;
import java.util.SortedSet;

@ExtendWith(MockitoExtension.class)
class ShowHistoryUseCaseTest {

    @InjectMocks
    ShowHistoryUseCase showHistoryUseCase;

    @Mock
    FindOrderOutPort findOrderOutPort;

    @Test
    void getHistory_ordersFound_ordersSorted() {
        Instant now = Instant.now();
        Mockito.when(findOrderOutPort.findOrdersByAccountId("acct_id")).thenReturn(Set.of(
                generateOrder(now), generateOrder(now.minusSeconds(360)), generateOrder(now.plusSeconds(360))
        ));

        OrderHistoryResponse orderHistoryResponse = showHistoryUseCase.getHistory(new HistoryQuery("acct_id"));

        Assertions.assertNotNull(orderHistoryResponse);
        Assertions.assertEquals("acct_id", orderHistoryResponse.accountId());

        SortedSet<Order> orders = orderHistoryResponse.orders();
        Assertions.assertEquals(3, orders.size());
        Assertions.assertEquals(now.minusSeconds(360), orders.last().createdAt());
        Assertions.assertEquals(now.plusSeconds(360), orders.first().createdAt());
    }

    @Test
    void getHistory_ordersNotFound_emptyResponse() {
        Mockito.when(findOrderOutPort.findOrdersByAccountId("acct_id")).thenReturn(Set.of());

        OrderHistoryResponse orderHistoryResponse = showHistoryUseCase.getHistory(new HistoryQuery("acct_id"));

        Assertions.assertNotNull(orderHistoryResponse);
        Assertions.assertEquals("acct_id", orderHistoryResponse.accountId());
        Assertions.assertEquals(0, orderHistoryResponse.orders().size());
    }

    Order generateOrder(Instant createdAt) {
        return new Order(
                "ordr_id_" + createdAt.toEpochMilli(),
                "acct_id",
                null,
                null,
                null,
                null,
                createdAt
        );
    }
}