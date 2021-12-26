package com.ai.st.microservice.providers.modules.shared.domain.exceptions;

import com.ai.st.microservice.providers.modules.shared.domain.DomainError;

public final class ManagerAliasInvalid extends DomainError {

    public ManagerAliasInvalid() {
        super("manager_alias_invalid", "El alias del gestor no es válido.");
    }

}
