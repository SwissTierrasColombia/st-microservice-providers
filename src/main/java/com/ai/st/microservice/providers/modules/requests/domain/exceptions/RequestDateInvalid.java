package com.ai.st.microservice.providers.modules.requests.domain.exceptions;

import com.ai.st.microservice.providers.modules.shared.domain.DomainError;

public final class RequestDateInvalid extends DomainError {

    public RequestDateInvalid() {
        super("request_date_invalid", "La fecha de solicitud no es válida.");
    }
}
