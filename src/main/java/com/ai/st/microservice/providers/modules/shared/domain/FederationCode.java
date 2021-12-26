package com.ai.st.microservice.providers.modules.shared.domain;

import com.ai.st.microservice.providers.modules.shared.domain.exceptions.FederationMunicipalityNameInvalid;

public final class FederationCode extends StringValueObject {

    private FederationCode(String value) {
        super(value);
    }

    private static void ensureMunicipalityCode(String value) {
        if (value == null || value.length() != 5) throw new FederationMunicipalityNameInvalid();
    }

    public static FederationCode fromValue(String value) {
        ensureMunicipalityCode(value);
        return new FederationCode(value);
    }

}
