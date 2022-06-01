package com.ai.st.microservice.providers.modules.requests.domain;

import com.ai.st.microservice.providers.modules.requests.domain.exceptions.RequestStatusNameInvalid;
import com.ai.st.microservice.providers.modules.shared.domain.StringValueObject;

public final class RequestStatusName extends StringValueObject {

    private RequestStatusName(String value) {
        super(value);
    }

    private static void ensureStatusName(String value) {
        if (value == null || value.isEmpty())
            throw new RequestStatusNameInvalid();
    }

    public static RequestStatusName fromValue(String value) {
        ensureStatusName(value);
        return new RequestStatusName(value);
    }

}
