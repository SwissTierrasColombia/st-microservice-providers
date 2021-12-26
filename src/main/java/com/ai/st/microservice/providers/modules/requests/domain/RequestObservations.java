package com.ai.st.microservice.providers.modules.requests.domain;

import com.ai.st.microservice.providers.modules.shared.domain.StringValueObject;

public final class RequestObservations extends StringValueObject {

    public RequestObservations(String value) {
        super(value);
    }

    public static RequestObservations fromValue(String value) {
        return new RequestObservations(value);
    }

}
