package com.ai.st.microservice.providers.modules.shared.domain;

import com.ai.st.microservice.providers.modules.shared.domain.exceptions.UserCodeInvalid;

public final class UserCode extends LongValueObject {

    private UserCode(Long value) {
        super(value);
    }

    private static void ensureCode(Long value) {
        if (value == null || value <= 0)
            throw new UserCodeInvalid(value);
    }

    public static UserCode fromValue(Long value) {
        ensureCode(value);
        return new UserCode(value);
    }
}
