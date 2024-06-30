package com.sokima.order.administration.java.domain;

public enum Status {
    /**
     * indicates that order was just created, but not in-progress yet
     * order details may be updated at this lifecycle phase
     */
    CREATED(0),

    /**
     * indicates that order was approved and ready for execution
     */
    APPROVED(3),

    /**
     * indicates that delivery agent took in work this order
     */
    IN_PROGRESS(4),

    /**
     * indicates that order was delivered to the shipping address and ready to be received by client
     */
    DELIVERED(5),

    /**
     * indicates that order was cancelled by someone
     * may be cancelled at any lifecycle phase (exception is `done` status)
     */
    CANCELLED(9),

    /**
     * indicates that order was paid and received by customer
     * the final status of order that cant be rollback
     */
    DONE(20),
    ;

    private final int step;

    Status(final int step) {
        this.step = step;
    }

    public int step() {
        return step;
    }
}
