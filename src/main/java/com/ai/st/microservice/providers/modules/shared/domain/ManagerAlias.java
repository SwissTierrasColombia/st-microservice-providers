package com.ai.st.microservice.providers.modules.shared.domain;

import com.ai.st.microservice.providers.modules.shared.domain.exceptions.ManagerAliasInvalid;

public final class ManagerAlias extends StringValueObject {

    private ManagerAlias(String value) {
        super(value);
    }

    private static void ensureAlias(String value) {
        if (value == null || value.isEmpty()) throw new ManagerAliasInvalid();
    }

    public static ManagerAlias fromValue(String value) {
        ensureAlias(value);
        return new ManagerAlias(value);
    }

}
