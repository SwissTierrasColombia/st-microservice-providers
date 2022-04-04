package com.ai.st.microservice.providers.modules.requests.domain;

import com.ai.st.microservice.providers.modules.requests.domain.exceptions.RequestDateInvalid;
import com.ai.st.microservice.providers.modules.shared.domain.DateObjectValue;

import java.util.Date;

public final class RequestDate extends DateObjectValue {

    private RequestDate(Date value) {
        super(value);
    }

    public static RequestDate fromValue(Date value) {
        ensureDate(value);
        return new RequestDate(value);
    }

    private static void ensureDate(Date value) {
        if (value == null)
            throw new RequestDateInvalid();
    }

}
