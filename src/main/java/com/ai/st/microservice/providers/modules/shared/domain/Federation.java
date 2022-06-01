package com.ai.st.microservice.providers.modules.shared.domain;

public final class Federation {

    private final FederationCode code;
    private final FederationMunicipality municipality;
    private final FederationState state;

    public Federation(FederationCode code, FederationMunicipality municipality, FederationState state) {
        this.code = code;
        this.municipality = municipality;
        this.state = state;
    }

    public static Federation fromPrimitives(String code, String municipality, String state) {
        return new Federation(FederationCode.fromValue(code), FederationMunicipality.fromValue(municipality),
                FederationState.fromValue(state));
    }

    public FederationCode code() {
        return code;
    }

    public FederationMunicipality municipality() {
        return municipality;
    }

    public FederationState state() {
        return state;
    }

}
