package com.ai.st.microservice.providers.modules.shared.domain;

import com.ai.st.microservice.providers.modules.shared.domain.exceptions.ManagerCodeInvalid;

public final class ManagerCode extends LongObjectValue {

    private ManagerCode(Long value) {
        super(value);
    }

    private static void ensureCode(Long value) {
        if (value == null || value <= 0) throw new ManagerCodeInvalid();
    }

    public static ManagerCode fromValue(Long value) {
        ensureCode(value);
        return new ManagerCode(value);
    }

}
