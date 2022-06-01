package com.ai.st.microservice.providers.modules.shared.domain;

import com.ai.st.microservice.providers.modules.shared.domain.exceptions.FederationMunicipalityNameInvalid;

public final class FederationMunicipality extends StringValueObject {

    private FederationMunicipality(String value) {
        super(value);
    }

    private static void ensureName(String value) {
        if (value == null || value.isEmpty())
            throw new FederationMunicipalityNameInvalid();
    }

    public static FederationMunicipality fromValue(String value) {
        ensureName(value);
        return new FederationMunicipality(value);
    }

}
