package com.ai.st.microservice.providers.modules.requests.application;

import com.ai.st.microservice.providers.modules.requests.domain.RequestStatus;
import com.ai.st.microservice.providers.modules.shared.application.Response;

public final class RequestStatusResponse implements Response {

    private final Long id;
    private final String name;

    public RequestStatusResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static RequestStatusResponse fromAggregate(RequestStatus status) {
        return new RequestStatusResponse(status.id().value(), status.name().value());
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }

}
