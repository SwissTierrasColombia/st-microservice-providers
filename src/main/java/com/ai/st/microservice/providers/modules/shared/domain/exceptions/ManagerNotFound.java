package com.ai.st.microservice.providers.modules.shared.domain.exceptions;

import com.ai.st.microservice.providers.modules.shared.domain.DomainError;

public final class ManagerNotFound extends DomainError {

    public ManagerNotFound() {
        super("manager_not_found", "No se ha encontrado el gestor.");
    }

    public ManagerNotFound(Long managerId) {
        super("manager_not_found", String.format("No se ha encontrado el gestor %d.", managerId));
    }
}
