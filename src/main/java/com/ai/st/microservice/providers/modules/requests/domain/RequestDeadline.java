package com.ai.st.microservice.providers.modules.requests.domain;

import com.ai.st.microservice.providers.modules.requests.domain.exceptions.RequestDeadlineInvalid;
import com.ai.st.microservice.providers.modules.shared.domain.DateObjectValue;

import java.util.Date;

public final class RequestDeadline extends DateObjectValue {

    private RequestDeadline(Date value) {
        super(value);
    }

    public static RequestDeadline fromValue(Date value) {
        ensureDeadline(value);
        return new RequestDeadline(value);
    }

    private static void ensureDeadline(Date value) {
        if (value == null)
            throw new RequestDeadlineInvalid();
    }

}
