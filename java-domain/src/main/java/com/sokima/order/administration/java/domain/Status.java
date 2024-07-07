package com.sokima.order.administration.java.domain;

public enum Status {
    /**
     * indicates that order was just created, but not in-progress yet
     * order details may be updated at this lifecycle phase
     */
    CREATED(0, "Order was created, awaiting approve."),

    /**
     * indicates that order was approved and ready for execution
     */
    APPROVED(3, "Order was approved, awaiting for delivery agent."),

    /**
     * indicates that delivery agent took in work this order
     */
    IN_PROGRESS(4, "Order delivering, await notification about arriving."),

    /**
     * indicates that order was delivered to the shipping address and ready to be received by client
     */
    DELIVERED(5, "Order was delivered, awaiting order to be received."),

    /**
     * indicates that order was cancelled by someone
     * may be cancelled at any lifecycle phase (exception is `done` status)
     */
    CANCELLED(9, "Order was cancelled."),

    /**
     * indicates that order was paid and received by customer
     * the final status of order that cant be rollback
     */
    DONE(20, "Order was completed."),
    ;

    private final int step;

    private final String description;

    Status(final int step, final String description) {
        this.step = step;
        this.description = description;
    }

    public int step() {
        return step;
    }

    public String description() {
        return description;
    }
}
