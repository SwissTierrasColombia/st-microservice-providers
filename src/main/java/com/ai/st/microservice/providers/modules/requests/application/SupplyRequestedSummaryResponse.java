package com.ai.st.microservice.providers.modules.requests.application;

import com.ai.st.microservice.providers.modules.requests.domain.SupplyRequested;
import com.ai.st.microservice.providers.modules.shared.application.Response;

public final class SupplyRequestedSummaryResponse implements Response {

    private final Long id;
    private final String name;
    private final String area;

    public SupplyRequestedSummaryResponse(Long id, String name, String area) {
        this.id = id;
        this.name = name;
        this.area = area;
    }

    public static SupplyRequestedSummaryResponse fromAggregate(SupplyRequested supplyRequested) {
        return new SupplyRequestedSummaryResponse(
                supplyRequested.id().value(), supplyRequested.name().value(), supplyRequested.area().value()
        );
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String area() {
        return area;
    }

}
