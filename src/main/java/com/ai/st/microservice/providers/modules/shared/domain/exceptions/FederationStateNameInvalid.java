package com.ai.st.microservice.providers.modules.shared.domain.exceptions;

import com.ai.st.microservice.providers.modules.shared.domain.DomainError;

public class FederationStateNameInvalid extends DomainError {

    public FederationStateNameInvalid() {
        super("federation_state_name_invalid", "El nombre del departamento no es válido");
    }
}
