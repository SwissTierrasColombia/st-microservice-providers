package com.ai.st.microservice.providers.modules.shared.application;

import com.ai.st.microservice.providers.modules.shared.domain.Federation;

public final class FederationResponse implements Response {

    private final String code;
    private final String municipality;
    private final String state;

    public FederationResponse(String code, String municipality, String state) {
        this.code = code;
        this.municipality = municipality;
        this.state = state;
    }

    public static FederationResponse fromAggregate(Federation federation) {
        return new FederationResponse(federation.code().value(), federation.municipality().value(),
                federation.state().value());
    }

    public String code() {
        return code;
    }

    public String municipality() {
        return municipality;
    }

    public String state() {
        return state;
    }

}
