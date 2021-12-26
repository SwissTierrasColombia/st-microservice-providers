package com.ai.st.microservice.providers.modules.areas.application;

import com.ai.st.microservice.providers.modules.areas.domain.Area;
import com.ai.st.microservice.providers.modules.shared.application.Response;

public final class AreaResponse implements Response {

    private final Long id;
    private final String name;

    public AreaResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static AreaResponse fromAggregate(Area area) {
        return new AreaResponse(area.areaId().value(), area.areaName().value());
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }
}
