package com.ai.st.microservice.providers.modules.areas.domain;

import com.ai.st.microservice.providers.modules.areas.domain.exceptions.AreaNameInvalid;
import com.ai.st.microservice.providers.modules.shared.domain.StringValueObject;

public final class AreaName extends StringValueObject {

    private AreaName(String value) {
        super(value);
    }

    private static void ensureAreaName(String value) {
        if (value == null || value.isEmpty()) throw new AreaNameInvalid(value);
    }

    public static AreaName fromValue(String value) {
        ensureAreaName(value);
        return new AreaName(value);
    }

}
