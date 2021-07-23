package com.ai.st.microservice.providers.modules.shared.domain.exceptions;

import com.ai.st.microservice.providers.modules.shared.domain.DomainError;

public class FederationMunicipalityNameInvalid extends DomainError {

    public FederationMunicipalityNameInvalid() {
        super("federation_municipality_name_invalid", "El nombre del municipio no es válido");
    }
}
