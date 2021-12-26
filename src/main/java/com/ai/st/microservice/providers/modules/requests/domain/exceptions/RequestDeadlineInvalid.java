package com.ai.st.microservice.providers.modules.requests.domain.exceptions;

import com.ai.st.microservice.providers.modules.shared.domain.DomainError;

public final class RequestDeadlineInvalid extends DomainError {

    public RequestDeadlineInvalid() {
        super("request_deadline_invalid", "La fecha límite no es válida.");
    }
}
