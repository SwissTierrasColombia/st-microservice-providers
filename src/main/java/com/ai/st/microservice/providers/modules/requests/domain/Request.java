package com.ai.st.microservice.providers.modules.requests.domain;

import com.ai.st.microservice.providers.modules.shared.domain.AggregateRoot;
import com.ai.st.microservice.providers.modules.shared.domain.Federation;
import com.ai.st.microservice.providers.modules.shared.domain.Manager;

import java.util.List;

public final class Request extends AggregateRoot {

    private final RequestId id;
    private final RequestClosedAt closedAt;
    private final RequestClosedBy closedBy;
    private final RequestDate createdAt;
    private final RequestDeadline deadline;
    private final RequestObservations observations;
    private final RequestOrderNumber orderNumber;
    private final RequestSentReviewAt sentReviewAt;
    private final Manager manager;
    private final Federation federation;
    private final RequestStatus status;
    private final List<SupplyRequested> suppliesRequested;

    public Request(RequestId id, RequestClosedAt closedAt, RequestClosedBy closedBy, RequestDate createdAt,
            RequestDeadline deadline, RequestObservations observations, RequestOrderNumber orderNumber,
            RequestSentReviewAt sentReviewAt, RequestStatus status, Manager manager, Federation federation,
            List<SupplyRequested> suppliesRequested) {
        this.id = id;
        this.closedAt = closedAt;
        this.closedBy = closedBy;
        this.createdAt = createdAt;
        this.deadline = deadline;
        this.observations = observations;
        this.orderNumber = orderNumber;
        this.sentReviewAt = sentReviewAt;
        this.status = status;
        this.manager = manager;
        this.federation = federation;
        this.suppliesRequested = suppliesRequested;
    }

    public RequestId id() {
        return id;
    }

    public RequestClosedAt closedAt() {
        return closedAt;
    }

    public RequestClosedBy closedBy() {
        return closedBy;
    }

    public RequestDate createdAt() {
        return createdAt;
    }

    public RequestDeadline deadline() {
        return deadline;
    }

    public RequestObservations observations() {
        return observations;
    }

    public RequestOrderNumber orderNumber() {
        return orderNumber;
    }

    public RequestSentReviewAt sentReviewAt() {
        return sentReviewAt;
    }

    public Manager manager() {
        return manager;
    }

    public Federation federation() {
        return federation;
    }

    public RequestStatus status() {
        return status;
    }

    public List<SupplyRequested> suppliesRequested() {
        return suppliesRequested;
    }
}
