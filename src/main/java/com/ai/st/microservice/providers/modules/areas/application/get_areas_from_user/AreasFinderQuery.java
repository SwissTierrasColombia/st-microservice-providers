package com.ai.st.microservice.providers.modules.areas.application.get_areas_from_user;

import com.ai.st.microservice.providers.modules.shared.application.Query;

public final class AreasFinderQuery implements Query {

    private final Long userId;

    public AreasFinderQuery(Long userId) {
        this.userId = userId;
    }

    public Long userId() {
        return userId;
    }
}
