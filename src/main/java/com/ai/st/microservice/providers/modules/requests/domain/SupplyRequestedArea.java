package com.ai.st.microservice.providers.modules.requests.domain;

import com.ai.st.microservice.providers.modules.requests.domain.exceptions.SupplyRequestedAreaInvalid;
import com.ai.st.microservice.providers.modules.shared.domain.StringValueObject;

public final class SupplyRequestedArea extends StringValueObject {

    private SupplyRequestedArea(String value) {
        super(value);
    }

    private static void ensureArea(String value) {
        if (value == null || value.isEmpty())
            throw new SupplyRequestedAreaInvalid();
    }

    public static SupplyRequestedArea fromValue(String value) {
        ensureArea(value);
        return new SupplyRequestedArea(value);
    }

}
