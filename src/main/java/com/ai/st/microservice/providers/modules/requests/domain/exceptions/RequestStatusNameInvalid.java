package com.ai.st.microservice.providers.modules.requests.domain.exceptions;

import com.ai.st.microservice.providers.modules.shared.domain.DomainError;

public final class RequestStatusNameInvalid extends DomainError {

    public RequestStatusNameInvalid() {
        super("request_status_name_invalid", "El nombre del estado no es válido.");
    }
}
