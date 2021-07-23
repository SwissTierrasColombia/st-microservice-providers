package com.ai.st.microservice.providers.modules.requests.domain;

import com.ai.st.microservice.providers.modules.shared.domain.LongObjectValue;

public final class SupplyRequestedId extends LongObjectValue {

    private SupplyRequestedId(Long value) {
        super(value);
    }

    public static SupplyRequestedId fromValue(Long value) {
        return new SupplyRequestedId(value);
    }

}
