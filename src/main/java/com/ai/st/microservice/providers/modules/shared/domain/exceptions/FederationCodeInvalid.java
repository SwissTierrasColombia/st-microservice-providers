package com.ai.st.microservice.providers.modules.shared.domain.exceptions;

import com.ai.st.microservice.providers.modules.shared.domain.DomainError;

public class FederationCodeInvalid extends DomainError {

    public FederationCodeInvalid() {
        super("federation_code_invalid", "El código del municipio no es válido");
    }
}
