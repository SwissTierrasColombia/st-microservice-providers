package com.ai.st.microservice.providers.modules.shared.application;

public final class NumberResponse implements Response {

    private final Long value;

    public NumberResponse(Long value) {
        this.value = value;
    }

    public Long value() {
        return value;
    }

}
