package com.ai.st.microservice.providers.modules.areas.domain.exceptions;

import com.ai.st.microservice.providers.modules.shared.domain.DomainError;

public final class AreaNameInvalid extends DomainError {

    public AreaNameInvalid(String areaName) {
        super("area_name_invalid", String.format("El nombre del área '%s' es inválido", areaName));
    }
}
