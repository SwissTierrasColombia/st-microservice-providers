package com.ai.st.microservice.providers.modules.requests.domain;

import com.ai.st.microservice.providers.modules.requests.domain.exceptions.RequestOrderNumberInvalid;
import com.ai.st.microservice.providers.modules.shared.domain.StringValueObject;

public final class RequestOrderNumber extends StringValueObject {

    private RequestOrderNumber(String value) {
        super(value);
    }

    private static void ensureOrderNumber(String value) {
        if (value == null || value.isEmpty())
            throw new RequestOrderNumberInvalid();
    }

    public static RequestOrderNumber fromValue(String value) {
        ensureOrderNumber(value);
        return new RequestOrderNumber(value);
    }

}
