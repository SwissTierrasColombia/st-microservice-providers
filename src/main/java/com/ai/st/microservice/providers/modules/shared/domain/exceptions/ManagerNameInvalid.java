package com.ai.st.microservice.providers.modules.shared.domain.exceptions;

import com.ai.st.microservice.providers.modules.shared.domain.DomainError;

public final class ManagerNameInvalid extends DomainError {

    public ManagerNameInvalid() {
        super("manager_name_invalid", "El nombre del gestor no es válido.");
    }

}
