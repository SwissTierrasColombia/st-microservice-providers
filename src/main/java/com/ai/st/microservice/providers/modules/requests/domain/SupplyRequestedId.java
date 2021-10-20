package com.ai.st.microservice.providers.modules.requests.domain;

import com.ai.st.microservice.providers.modules.shared.domain.LongValueObject;

public final class SupplyRequestedId extends LongValueObject {

    private SupplyRequestedId(Long value) {
        super(value);
    }

    public static SupplyRequestedId fromValue(Long value) {
        return new SupplyRequestedId(value);
    }

}
