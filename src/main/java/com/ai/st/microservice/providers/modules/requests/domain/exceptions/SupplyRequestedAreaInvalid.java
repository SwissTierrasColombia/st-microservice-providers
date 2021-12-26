package com.ai.st.microservice.providers.modules.requests.domain.exceptions;

import com.ai.st.microservice.providers.modules.shared.domain.DomainError;

public final class SupplyRequestedAreaInvalid extends DomainError {

    public SupplyRequestedAreaInvalid() {
        super("supply_requested_area_invalid", "El nombre del área no es válido.");
    }
}
