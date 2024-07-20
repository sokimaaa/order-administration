package com.sokima.order.administration.usecase.port;

import com.sokima.order.administration.usecase.query.in.HistoryQuery;
import com.sokima.order.administration.usecase.query.out.OrderHistoryResponse;

public interface ShowHistoryInPort {
    OrderHistoryResponse getHistory(final HistoryQuery historyQuery);
}
