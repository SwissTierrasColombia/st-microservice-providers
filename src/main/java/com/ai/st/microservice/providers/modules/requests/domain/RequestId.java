package com.ai.st.microservice.providers.modules.requests.domain;

import com.ai.st.microservice.providers.modules.shared.domain.LongValueObject;

public final class RequestId extends LongValueObject {

    public RequestId(Long value) {
        super(value);
    }

    public static RequestId fromValue(Long value) {
        return new RequestId(value);
    }

}
