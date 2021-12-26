package com.ai.st.microservice.providers.modules.requests.application;

import com.ai.st.microservice.providers.modules.requests.domain.Request;
import com.ai.st.microservice.providers.modules.shared.application.FederationResponse;
import com.ai.st.microservice.providers.modules.shared.application.ManagerResponse;
import com.ai.st.microservice.providers.modules.shared.application.Response;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public final class RequestResponse implements Response {

    private final Long id;
    private final Date closedAt;
    private final Long closedBy;
    private final Date createdAt;
    private final Date deadline;
    private final String observations;
    private final String orderNumber;
    private final RequestStatusResponse status;
    private final ManagerResponse manager;
    private final FederationResponse federation;
    private final List<SupplyRequestedSummaryResponse> suppliesSummary;

    public RequestResponse(Long id, Date closedAt, Long closedBy, Date createdAt, Date deadline,
                           String observations, String orderNumber, RequestStatusResponse status,
                           ManagerResponse manager, FederationResponse federation,
                           List<SupplyRequestedSummaryResponse> suppliesSummary) {
        this.id = id;
        this.closedAt = closedAt;
        this.closedBy = closedBy;
        this.createdAt = createdAt;
        this.deadline = deadline;
        this.observations = observations;
        this.orderNumber = orderNumber;
        this.status = status;
        this.manager = manager;
        this.federation = federation;
        this.suppliesSummary = suppliesSummary;
    }

    public static RequestResponse fromAggregate(Request request) {
        return new RequestResponse(
                request.id().value(),
                request.closedAt().value(),
                request.closedBy().value(),
                request.createdAt().value(),
                request.deadline().value(),
                request.observations().value(),
                request.orderNumber().value(),
                RequestStatusResponse.fromAggregate(request.status()),
                ManagerResponse.fromAggregate(request.manager()),
                FederationResponse.fromAggregate(request.federation()),
                request.suppliesRequested().stream().map(SupplyRequestedSummaryResponse::fromAggregate).collect(Collectors.toList())
        );
    }

    public Long id() {
        return id;
    }

    public Date closedAt() {
        return closedAt;
    }

    public Long closedBy() {
        return closedBy;
    }

    public Date createdAt() {
        return createdAt;
    }

    public Date deadline() {
        return deadline;
    }

    public String observations() {
        return observations;
    }

    public String orderNumber() {
        return orderNumber;
    }

    public ManagerResponse manager() {
        return manager;
    }

    public FederationResponse federation() {
        return federation;
    }

    public RequestStatusResponse status() {
        return status;
    }

    public List<SupplyRequestedSummaryResponse> suppliesSummary() {
        return suppliesSummary;
    }

}
