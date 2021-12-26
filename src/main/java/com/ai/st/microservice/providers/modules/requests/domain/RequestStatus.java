package com.ai.st.microservice.providers.modules.requests.domain;

import com.ai.st.microservice.providers.modules.shared.domain.AggregateRoot;

public final class RequestStatus extends AggregateRoot {

    private final RequestStatusId id;
    private final RequestStatusName name;

    public RequestStatus(RequestStatusId id, RequestStatusName name) {
        this.id = id;
        this.name = name;
    }

    public static RequestStatus fromPrimivites(Long id, String name) {
        return new RequestStatus(
                RequestStatusId.fromValue(id),
                RequestStatusName.fromValue(name)
        );
    }

    public RequestStatusId id() {
        return id;
    }

    public RequestStatusName name() {
        return name;
    }

}
