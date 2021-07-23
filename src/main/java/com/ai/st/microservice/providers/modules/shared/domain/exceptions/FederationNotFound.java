package com.ai.st.microservice.providers.modules.shared.domain.exceptions;

import com.ai.st.microservice.providers.modules.shared.domain.DomainError;

public final class FederationNotFound extends DomainError {

    public FederationNotFound() {
        super("federation_not_found", "No se ha encontrado la ubicación de la solicitud.");
    }
}
