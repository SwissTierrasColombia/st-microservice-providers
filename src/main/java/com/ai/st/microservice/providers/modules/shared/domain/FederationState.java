package com.ai.st.microservice.providers.modules.shared.domain;

import com.ai.st.microservice.providers.modules.shared.domain.exceptions.FederationStateNameInvalid;

public final class FederationState extends StringValueObject {

    private FederationState(String value) {
        super(value);
    }

    private static void ensureName(String value) {
        if (value == null || value.isEmpty()) throw new FederationStateNameInvalid();
    }

    public static FederationState fromValue(String value) {
        ensureName(value);
        return new FederationState(value);
    }

}
