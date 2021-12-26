package com.ai.st.microservice.providers.modules.requests.domain.exceptions;

import com.ai.st.microservice.providers.modules.shared.domain.DomainError;

public final class SupplyRequestedNameInvalid extends DomainError {

    public SupplyRequestedNameInvalid() {
        super("supply_requested_name_invalid", "El nombre del insumo solicitado no es válido.");
    }
}
