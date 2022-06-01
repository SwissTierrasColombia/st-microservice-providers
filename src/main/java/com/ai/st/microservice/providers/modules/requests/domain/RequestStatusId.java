package com.ai.st.microservice.providers.modules.requests.domain;

import com.ai.st.microservice.providers.modules.requests.domain.exceptions.RequestStatusInvalid;
import com.ai.st.microservice.providers.modules.shared.domain.LongValueObject;

public final class RequestStatusId extends LongValueObject {

    public static final Long REQUESTED = (long) 1;
    public static final Long DELIVERED = (long) 2;
    public static final Long CANCELLED = (long) 3;

    private RequestStatusId(Long value) {
        super(value);
    }

    private static void ensureStatus(Long value) {
        if (!value.equals(RequestStatusId.REQUESTED) && !value.equals(RequestStatusId.DELIVERED)
                && !value.equals(RequestStatusId.CANCELLED))
            throw new RequestStatusInvalid();
    }

    public static RequestStatusId fromValue(Long value) {
        ensureStatus(value);
        return new RequestStatusId(value);
    }

}
