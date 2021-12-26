package com.ai.st.microservice.providers.modules.requests.domain;

import com.ai.st.microservice.providers.modules.shared.domain.DateObjectValue;

import java.util.Date;

public final class RequestClosedAt extends DateObjectValue {

    public RequestClosedAt(Date value) {
        super(value);
    }

    public static RequestClosedAt fromValue(Date value) {
        return new RequestClosedAt(value);
    }

}
