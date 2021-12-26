package com.ai.st.microservice.providers.modules.requests.domain;

import com.ai.st.microservice.providers.modules.shared.domain.LongValueObject;

public final class RequestClosedBy extends LongValueObject {

    public RequestClosedBy(Long value) {
        super(value);
    }

    public static RequestClosedBy fromValue(Long value) {
        return new RequestClosedBy(value);
    }

}
