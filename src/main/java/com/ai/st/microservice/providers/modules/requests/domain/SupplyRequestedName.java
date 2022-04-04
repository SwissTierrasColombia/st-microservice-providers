package com.ai.st.microservice.providers.modules.requests.domain;

import com.ai.st.microservice.providers.modules.requests.domain.exceptions.SupplyRequestedNameInvalid;
import com.ai.st.microservice.providers.modules.shared.domain.StringValueObject;

public final class SupplyRequestedName extends StringValueObject {

    private SupplyRequestedName(String value) {
        super(value);
    }

    private static void ensureName(String value) {
        if (value == null || value.isEmpty())
            throw new SupplyRequestedNameInvalid();
    }

    public static SupplyRequestedName fromValue(String value) {
        ensureName(value);
        return new SupplyRequestedName(value);
    }

}
