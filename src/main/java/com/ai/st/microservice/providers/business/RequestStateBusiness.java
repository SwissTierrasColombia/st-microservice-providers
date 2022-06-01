package com.ai.st.microservice.providers.business;

import org.springframework.stereotype.Component;

@Component
public class RequestStateBusiness {

    public static final Long REQUEST_STATE_REQUESTED = (long) 1;
    public static final Long REQUEST_STATE_DELIVERED = (long) 2;
    public static final Long REQUEST_STATE_CANCELLED = (long) 3;

}
