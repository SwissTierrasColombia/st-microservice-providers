package com.ai.st.microservice.providers.modules.requests.domain.exceptions;

import com.ai.st.microservice.providers.modules.shared.domain.DomainError;

public final class RequestOrderNumberInvalid extends DomainError {

    public RequestOrderNumberInvalid() {
        super("request_order_number_invalid", "El número de orden de la solicitud no es válido.");
    }
}
