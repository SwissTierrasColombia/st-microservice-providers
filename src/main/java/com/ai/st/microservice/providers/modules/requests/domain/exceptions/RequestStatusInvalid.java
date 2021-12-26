package com.ai.st.microservice.providers.modules.requests.domain.exceptions;

import com.ai.st.microservice.providers.modules.shared.domain.DomainError;

public final class RequestStatusInvalid extends DomainError {

    public RequestStatusInvalid() {
        super("request_status_invalid", "El estado de solicitud no es válido.");
    }
}
