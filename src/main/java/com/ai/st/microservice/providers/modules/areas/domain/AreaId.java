package com.ai.st.microservice.providers.modules.areas.domain;

import com.ai.st.microservice.providers.modules.areas.domain.exceptions.AreaIdInvalid;
import com.ai.st.microservice.providers.modules.shared.domain.LongValueObject;

public final class AreaId extends LongValueObject {

    private AreaId(Long value) {
        super(value);
    }

    private static void ensureAreaId(Long value) {
        if (value == null || value <= 0)
            throw new AreaIdInvalid(value);
    }

    public static AreaId fromValue(Long value) {
        ensureAreaId(value);
        return new AreaId(value);
    }

}
