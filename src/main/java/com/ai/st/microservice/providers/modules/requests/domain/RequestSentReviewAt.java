package com.ai.st.microservice.providers.modules.requests.domain;

import com.ai.st.microservice.providers.modules.shared.domain.DateObjectValue;

import java.util.Date;

public final class RequestSentReviewAt extends DateObjectValue {

    public RequestSentReviewAt(Date value) {
        super(value);
    }

    public static RequestSentReviewAt fromValue(Date value) {
        return new RequestSentReviewAt(value);
    }

}
