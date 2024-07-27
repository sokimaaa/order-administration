package com.sokima.order.administration.usecase.query;

import com.google.common.collect.ImmutableSortedSet;
import com.sokima.order.administration.java.domain.Order;
import com.sokima.order.administration.java.domain.port.out.FindOrderOutPort;
import com.sokima.order.administration.usecase.port.ShowHistoryInPort;
import com.sokima.order.administration.usecase.query.in.HistoryQuery;
import com.sokima.order.administration.usecase.query.out.OrderHistoryResponse;

import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;

public final class ShowHistoryUseCase implements ShowHistoryInPort {

    private static final Comparator<Order> BY_DESCENDING_ORDER_CREATED = Comparator.comparing(Order::createdAt).reversed();

    private final FindOrderOutPort findOrderOutPort;

    public ShowHistoryUseCase(final FindOrderOutPort findOrderOutPort) {
        this.findOrderOutPort = findOrderOutPort;
    }

    @Override
    public OrderHistoryResponse getHistory(final HistoryQuery historyQuery) {
        final Set<Order> orders = findOrderOutPort.findOrdersByAccountId(historyQuery.accountId());
        final SortedSet<Order> sortedOrders = ImmutableSortedSet.copyOf(BY_DESCENDING_ORDER_CREATED, orders);
        return new OrderHistoryResponse(
                sortedOrders, historyQuery.accountId()
        );
    }
}
