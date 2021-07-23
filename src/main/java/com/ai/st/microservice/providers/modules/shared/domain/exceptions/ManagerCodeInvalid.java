package com.ai.st.microservice.providers.modules.shared.domain.exceptions;

import com.ai.st.microservice.providers.modules.shared.domain.DomainError;

public final class ManagerCodeInvalid extends DomainError {

    public ManagerCodeInvalid() {
        super("manager_code_invalid", "El código del gestor no es válido.");
    }

}
